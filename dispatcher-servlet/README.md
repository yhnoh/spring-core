# DispatcherServlet (디스패처 서블릿)


## [Notion](https://superb-ermine-a50.notion.site/DispatcherServlet-95b059327e564aabb600063a6d909d1e)


### 목표

- dispatcher-servlet의 개념
- 동작 원리
- 사용이유

### DispatcherServlet의 개념

- dispatcher는 보내다
- 디스패처 서블릿은 **가장 앞단에서 HTTP 프로토콜로 들어오는 모든 요청을 가장 먼저 받아 적합한 컨트롤러에게 위임**해주는 프론트 컨트롤러
- 프론트 컨트롤러란 서버로 들어오는 클라이언트의 모든 요청을 처리해주는 컨트롤러

### 동작원리

- 핵심은 **적합한 컨트롤러와 메소드를 찾아 요청을 위임**


1. 클라이언트의 요청을 디스패처 서블릿이 받음
2. 요청 정보를 통해 요청을 위임함 핸들러(컨트롤러)를 찾음
3. 요청을 컨트롤러로 위임 처리할 핸들러 어댑터를 찾음
4. 핸들러 어댑터가 핸들러(컨트롤러)로 요청을 위임함
5. 비지니스 로직이 처리됨
6. 컨트롤러가 ResponseEntity를 반환함
7. HandlerAdapter가 반환받은 ResponseEntity를 통해 Response 처리를 진행함
8. 서버의 응답을 클라이언트로 반환함

### 동작원리 상세

- dispatcher-servlet은 모든 컨트롤러를 파싱하여 HashMap으로 (요청 정보, 요청을 처리할 대상) 관리
- 요청이 들어올 경우 요청 정보 객체를 만들어 Map에서 요청을 처리하는 컨트롤러 및 메소드찾음
- dispatcher-servlet은 컨트롤러로 요청을 위임할 adapter(RequestMappingHandlerAdapter)를 찾은 후에 해당 adapter를 통해 컨트롤러 요청을 위임
- 요청을 처리할 컨트롤러의 메소드가 존재하면 Reflection으로 컨트롤러의 메소드를 호출
- 컨트롤러가 ResponseEntity를 반환하면 MessageConverter 등을 통해 응답 처리를 진행 후에 결과를 클라이언트에게 반환

```java
public class DispatcherServlet extends FrameworkServlet {
	...
	protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    HttpServletRequest processedRequest = request;
	    HandlerExecutionChain mappedHandler = null;
	    boolean multipartRequestParsed = false;
	    WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);
	
	    try {
	        try {
	            ModelAndView mv = null;
	            Object dispatchException = null;
	
	            try {
	                processedRequest = this.checkMultipart(request);
	                multipartRequestParsed = processedRequest != request;
									//요청 매핑 정보와 인터셉터 정보 조회
	                mappedHandler = this.getHandler(processedRequest);
									//404에러 처리
	                if (mappedHandler == null) {
	                    this.noHandlerFound(processedRequest, response);
	                    return;
	                }
									//요청을 처리할 HandlerAdapter 조회
	                HandlerAdapter ha = this.getHandlerAdapter(mappedHandler.getHandler());
	                String method = request.getMethod();
	                boolean isGet = HttpMethod.GET.matches(method);
	                if (isGet || HttpMethod.HEAD.matches(method)) {
	                    long lastModified = ha.getLastModified(request, mappedHandler.getHandler());
	                    if ((new ServletWebRequest(request, response)).checkNotModified(lastModified) && isGet) {
	                        return;
	                    }
	                }
									//컨트롤러 진입전 인터셉터 실행
	                if (!mappedHandler.applyPreHandle(processedRequest, response)) {
	                    return;
	                }
									//컨트롤러 메소드 실행
	                mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
	                if (asyncManager.isConcurrentHandlingStarted()) {
	                    return;
	                }
				
	                this.applyDefaultViewName(processedRequest, mv);
									//컨트롤러 응답 완료 이후 인터셉터 실행
	                mappedHandler.applyPostHandle(processedRequest, response, mv);
	            } catch (Exception var20) {
	                dispatchException = var20;
	            } catch (Throwable var21) {
	                dispatchException = new NestedServletException("Handler dispatch failed", var21);
	            }
	
	            this.processDispatchResult(processedRequest, response, mappedHandler, mv, (Exception)dispatchException);
	        } catch (Exception var22) {
	            this.triggerAfterCompletion(processedRequest, response, mappedHandler, var22);
	        } catch (Throwable var23) {
	            this.triggerAfterCompletion(processedRequest, response, mappedHandler, new NestedServletException("Handler processing failed", var23));
	        }
	
	    } finally {
	        if (asyncManager.isConcurrentHandlingStarted()) {
	            if (mappedHandler != null) {
	                mappedHandler.applyAfterConcurrentHandlingStarted(processedRequest, response);
	            }
	        } else if (multipartRequestParsed) {
	            this.cleanupMultipart(processedRequest);
	        }
	
	    }
	}
}

```

1. HandlerExecutionChain : 요청 매핑 정보와 인터셉터 실행을 위한 조회
    - HandlerExecutionChain는 **HandlerMethod와 Interceptor들로 구성**
    - HandlerMethod에는 매핑되는 **컨트롤러의 메소드와 컨트롤러 빈(또는 빈 이름) 및 빈 팩토리 등이 저장**
    - `mappedHandler = this.getHandler(processedRequest);`를 통해서 조회
2. HandlerAdapter : 요청을 처리할 adapter가 조회될 경우 컨트롤러 메소드 호출
    - HandlerAdapter의 인터페이스를 통해서 컨트롤러를 실행한다.
    - HandlerExecutionChain을 사용하지 않고 HandlerAdapter를 사용하는 이유는 **컨트롤러에 대해 공통적인 전/후처리를 위함**
    - `HandlerAdapter ha = this.getHandlerAdapter(mappedHandler.getHandler())`로 조회한 이후 `mv = ha.handle(processedRequest, response, mappedHandler.getHandler())`로 컨트롤러를 실행한다.
    - 컨트롤러의 파라미터를 처리하는 ArgumentResolver와 반환값을 처리하는 ReturnValueHandler가 존재
    
    ### 사용 이유
    

> [https://mangkyu.tistory.com/18](https://mangkyu.tistory.com/18)
> 

> [https://mangkyu.tistory.com/216](https://mangkyu.tistory.com/216)
>