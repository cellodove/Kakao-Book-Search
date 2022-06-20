
### 카카오페이 Android 과제 템플릿

템플릿에 선언된 **모든 사항(Architecture, DI, Network...)은 권장 사항이 아니에요.** 😄   
지원자께서 **자유롭고 편한 방법**으로 개발해주세요.

그리고 개발하기 시작전에 Kakao Develepers에서 발급 받은 REST API Key를 아래 `{API_KEY}`에 넣어주세요!

``` kotlin
 private fun getHttpClient() = OkHttpClient.Builder()
        .readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .addInterceptor {
            val request = it.request()
                .newBuilder()
                // TODO. 발급받은 API Key를 입력하세요.
                .addHeader("Authorization", "KakaoAK {API_KEY}")
                .build()
            it.proceed(request)
        }.build()
```

  

**책 검색 API 가이드**  
https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-book

**과제 가이드**  
https://kakaopayworkspace.notion.site/Android-2e715e3652b344e88e3614cc10234bd8
