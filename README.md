# 카카오 도서API를 사용해 어플 만들기

카카오 도서 api를 사용해 어플을 만들었습니다. 어플을 만드는데 사용한 기술들은 아래와 같습니다.

## 기술

### Language

- Kotlin

### Architecture

- MVVM

### Library

- Retrofit2
- Glide
- Paging3

### Design Pattern

- Singleton
- Repository
- Clean Architecture

### DI

- Hilt

### Etc

- Coroutine - Flow
- Multi Module
- Navigation Component
- Lifecycle
- ViewBinding

## 구조

먼저 멀티모듈로 클린아키텍처 구조를 만들었습니다. 그리고 MVVM패턴을 사용했습니다.

- buildSrc - dependency 관리
- domain - UseCase 및 Model
- data - 서버 통신 및 모델 변환
- presentation - 화면과 입력에 대한 처리
- app - DI 세팅

모듈별로 앱을 설명해 나가겠습니다.

![kakao_image1.png](/image/kakao_image1.png)

## ****buildSrc****

모듈이 많다보니 dependency를 관리가 까다롭습니다. 이때 buildSrc를 사용해 한번해 관리할 수가있습니다.

- Dependencies.kt

```kotlin
object Versions {
    const val KOTLINX_COROUTINES = "1.5.0"
    const val STDLIB ="1.6.21"

    const val CORE_KTX = "1.8.0"
    const val APP_COMPAT = "1.4.2"
    const val ACTIVITY_KTX = "1.4.0"
    const val FRAGMENT_KTX = "1.4.1"

    const val LIFECYCLE_KTX = "2.4.1"

    const val HILT = "2.35.1"
    const val MATERIAL = "1.6.1"

    const val RETROFIT = "2.9.0"
    const val OKHTTP = "4.9.3"

    const val JUNIT = "4.13.2"
    const val ANDROID_JUNIT = "1.1.2"
    const val ESPRESSO_CORE = "3.4.0"

    const val GLIDE_VER = "4.12.0"
    const val PAGING_VERSION = "3.1.1"
}

object Kotlin {
    const val KOTLIN_STDLIB      = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.STDLIB}"
    const val COROUTINES_CORE    = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLINX_COROUTINES}"
}

object AndroidX {
    const val CORE_KTX                = "androidx.core:core-ktx:${Versions.CORE_KTX}"
    const val APP_COMPAT              = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"

    const val ACTIVITY_KTX            = "androidx.activity:activity-ktx:${Versions.ACTIVITY_KTX}"
    const val FRAGMENT_KTX            = "androidx.fragment:fragment-ktx:${Versions.FRAGMENT_KTX}"

    const val LIFECYCLE_VIEWMODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE_KTX}"
    const val LIFECYCLE_LIVEDATA_KTX  = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE_KTX}"
}

object Google {
    const val HILT_ANDROID          = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
}

object JetpackPack{
    const val NAVIGATION_FRAGMENT  = "androidx.navigation:navigation-fragment-ktx:2.4.2"
    const val NAVIGATION_UI        = "androidx.navigation:navigation-ui-ktx:2.4.2"
    const val NAVIGATION_DYNAMIC   = "androidx.navigation:navigation-dynamic-features-fragment:2.4.2"
    const val NAVIGATION_TEST_IMP = "androidx.navigation:navigation-testing:2.4.2"
}

object Libraries {
    const val RETROFIT                   = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val RETROFIT_CONVERTER_GSON    = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
    const val OKHTTP_LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"
    const val GLIDE = "com.github.bumptech.glide:glide:${Versions.GLIDE_VER}"
    const val GLIDE_ANNOTATION = "com.github.bumptech.glide:compiler:${Versions.GLIDE_VER}"
}

object Test {
    const val JUNIT         = "junit:junit:${Versions.JUNIT}"
    const val ANDROID_JUNIT = "androidx.test.ext:junit:${Versions.ANDROID_JUNIT}"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
}

object Paging{
    const val PAGING_RUNTIME = "androidx.paging:paging-runtime:${Versions.PAGING_VERSION}"
    const val PAGING_COMMON  = "androidx.paging:paging-common:${Versions.PAGING_VERSION}"
}
```

- build.gradle(:app)

```kotlin
dependencies {
    implementation project(":data")
    implementation project(":domain")
    implementation project(":presentation")

    implementation(AndroidX.CORE_KTX)
    implementation(AndroidX.APP_COMPAT)
    implementation(Kotlin.KOTLIN_STDLIB)

    implementation(Libraries.RETROFIT)
    implementation(Libraries.RETROFIT_CONVERTER_GSON)
    implementation(Libraries.OKHTTP_LOGGING_INTERCEPTOR)

    implementation(Google.HILT_ANDROID)
    kapt (Google.HILT_ANDROID_COMPILER)

    implementation(Paging.PAGING_RUNTIME)
    implementation(Paging.PAGING_COMMON)

    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
}
```

## Domain 계층

![kakao_image2.png](/image/kakao_image2.png)

### Model

- SearchBookResponse

```kotlin
data class SearchBookResponse(
    val meta : Meta,
    val documents : List<Documents>
)

data class Meta(
    val total_count : Int,
    val pageable_count : Int,
    val is_end : Boolean
)

data class Documents(
    val title : String,
    val contents: String,
    val url : String,
    val isbn : String,
    val datetime : String,
    val authors : List<String>,
    val publisher : String,
    val translators : List<String>,
    val price : Int
    val sale_price : Int,
    val thumbnail : String,
    val status : String
)
```

Domain 계층의 Model입니다. BookRepository의 정보를 가지고 있으며 안드로이드의 의존성을 갖지 않도록 작성해줍니다. 다만 데이터를 표출할때 Paging3 라이브러리를 사용하는데 페이징데이터를 가지고와야하기 때문에 페이징관련 라이브러리는 추가해주어야합니다.

### **Repository**

- BookRepository

```kotlin
interface BookRepository {
    fun searchBookPaging(userQuery: String) : Flow<PagingData<Documents>>
}
```

도서 API의 Repository 목록을 가져오기 위한 Repository의 인터페이스를 만들어줍니다. BookRepository의 구현체는 Data 계층에 위치합니다.

### **UseCase**

- SearchBookPagingUseCase

```kotlin
class SearchBookPagingUseCase(private val kakaoSearchBookRepository : BookRepository) {
    fun getBookPagingData(userQuery: String, scope : CoroutineScope):Flow<PagingData<Documents>>{
        return kakaoSearchBookRepository.searchBookPaging(userQuery).cachedIn(scope)
    }
}
```

도서 API에서 Repository 목록을 가져오는 기능을 제공하는 유스케이스입니다. BookRepository를 생성자로 주입받아 데이터를 가져오는 역할을 합니다.

## Data 계층

![kakao_image3.png](/image/kakao_image3.png)

### Model

- BookResponse

```kotlin
data class BookResponse(
    @SerializedName("meta") val meta: BookMetaResponse,
    @SerializedName("documents") val documents: List<BookItemResponse>
)

data class BookMetaResponse(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("pageable_count") val pageableCount: Int,
    @SerializedName("is_end") val isEnd: Boolean
)

data class BookItemResponse(
    @SerializedName("title") val title: String,
    @SerializedName("contents") val contents: String,
    @SerializedName("url") val url: String,
    @SerializedName("isbn") val isbn: String,
    @SerializedName("datetime") val datetime: String,
    @SerializedName("authors") val authors: List<String>,
    @SerializedName("publisher") val publisher: String,
    @SerializedName("translators") val translators: List<String>,
    @SerializedName("price") val price: Int,
    @SerializedName("sale_price") val salePrice: Int,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("status") val status: String
)
```

도서 API를 사용해 데이터를 가져올 model입니다.

### Mapper

```kotlin
fun mapperToDocumentsList(bookItemResponse:List<BookItemResponse>) : List<Documents>{
    return bookItemResponse.toList().map {
        Documents(
            it.title,
            it.contents,
            it.url,
            it.isbn,
            it.datetime,
            it.authors,
            it.publisher,
            it.translators,
            it.price,
            it.salePrice,
            it.thumbnail,
            it.status
        )
    }
}
```

mapper를 사용해 서버에서오는 데이터 `BookItemResponse`를 domain 데이터 `Documents` 로 변환해 줍니다.

### Service

- KakaoBookService

```kotlin
interface KakaoBookService {

    @GET("/v3/search/book")
    suspend fun getBooks(
        @Header(value = "Authorization") token : String,
        @Query(value = "query") userQuery: String,
        @Query(value = "page") page: Int,
        @Query(value = "size") size: Int
    ): BookResponse
}
```

도서 API를 호출할 수 있게 레트로핏으로 세팅해줍니다.

### Source

- BookRemotePagingDataSource

```kotlin
private const val SEARCH_STARTING_PAGE_INDEX = 1

class BookRemotePagingDataSource @Inject constructor(
    private val kakaoBookService: KakaoBookService,
    private val query : String
) : PagingSource<Int, Documents>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Documents> {
        val position = params.key ?: SEARCH_STARTING_PAGE_INDEX
        val apiQuery = query
        return try {
            val response =  kakaoBookService.getBooks("KakaoAK 909689c173c91d9b3ea428891711edd1", apiQuery, position, 50)
            val bookResponse = response.documents
            val nextKey = if (response.documents.isEmpty()){
                null
            }else{
                position + 1
            }
            LoadResult.Page(data = mapperToDocumentsList(bookResponse), prevKey = if (position == SEARCH_STARTING_PAGE_INDEX) null else position - 1, nextKey = nextKey)
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Documents>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
```

페이징을 사용하기에 페이징에 맞는 데이터를 가져와야합니다. 그래서 PagingSource를 사용해 DataSource를 만들어줍니다.

### RepositoryImpl

- BookRepositoryImpl

```kotlin
class BookRepositoryImpl @Inject constructor(
    private val kakaoBookService : KakaoBookService
): BookRepository {
    override fun searchBookPaging(userQuery: String): Flow<PagingData<Documents>> {
        return Pager(config = PagingConfig(pageSize = NETWORK_PAGE_SIZE,enablePlaceholders = false),
            pagingSourceFactory = { BookRemotePagingDataSource(kakaoBookService,userQuery) }).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }
}
```

Domain 계층의 BookRepository 인터페이스를 구현합니다.

KakaoBookService를 생성자로 주입받아 BookRemotePagingDataSource로 전달해 데이터를 가져오게 됩니다.

## ****Presentation 계층****

![kakao_image4.png](/image/kakao_image4.png)

### Manifest

```kotlin
<activity
	android:configChanges="keyboardHidden|orientation|screenSize"/>
```

화면을 회전하게되면 액티비티가 리셋되어 데이터가 다 날아가게됩니다. 그걸 막기위해 해당 코드를 추가해주었습니다. 

### Base

중복 되는 코드들을 따로빼서 관리해줍니다. 여기서는 간단하게 컴포넌트별 기본으로 구현해야하는 내용만 작성하였습니다.

- BaseActivity

```kotlin
abstract class BaseActivity<VB: ViewBinding>(private val bindingInflater:(inflater: LayoutInflater) -> VB) : AppCompatActivity() {
    lateinit var binding : VB
    abstract fun observeViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingInflater.invoke(layoutInflater)
        setContentView(binding.root)
        observeViewModel()
    }
}
```

- BaseFragment

```kotlin
abstract class BaseFragment<VB: ViewBinding>(private val bindingInflater:(inflater: LayoutInflater) -> VB) : Fragment() {
    lateinit var binding : VB
    abstract fun observeViewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingInflater.invoke(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }
}
```

### Main

- MainViewModel

```kotlin
@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchBookPagingUseCase : SearchBookPagingUseCase
) : ViewModel() {
    private val checkLikeList = ArrayList<String>()

    fun searchBook(queryString: String): Flow<PagingData<Documents>> {
        return searchBookPagingUseCase.getBookPagingData(queryString,viewModelScope).cachedIn(viewModelScope)
    }

    fun addCheckLike(title : String){
        checkLikeList.add(title)
    }

    fun deleteCheckLike(title : String){
        checkLikeList.remove(title)
    }

    fun getCheckLikeList():ArrayList<String>{
        return checkLikeList
    }

    fun deleteAllLikeList(){
        checkLikeList.clear()
    }
}
```

ViewModel에서는 Domain 계층의 유스케이스를 주입받아 데이터를 가져옵니다. Presentation 계층에서는 Data 계층의 의존성이 없기 때문에 Data 계층 데이터를 가져오는 구현체에 직접적으로 접근은 불가능합니다.

좋아요버튼 처리를 위해 뷰모델에서 `checkLikeList` 데이터를 관리합니다.

- MainActivity

```kotlin
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun observeViewModel() = Unit

    companion object{
        const val TITLE = "title"
        const val THUMBNAIL = "thumbnail"
        const val PRICE = "price"
        const val PUBLISHER = "publisher"
        const val CONTENTS = "contents"
    }
}
```

네비게이션 컴포넌트를 사용하여 액티비티에는 따로 작성을 하지 않았습니다.

### navigation

```xml
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/nav_main"
    app:startDestination="@id/BookSearchFragment">

    <fragment
        android:id="@+id/BookSearchFragment"
        android:name="com.cellodove.presentation.ui.search.SearchFragment"
        tools:layout="@layout/fragment_search">
        <action
            android:id="@+id/openBookLaunchDetails"
            app:destination="@+id/BookDetailsFragment"/>
    </fragment>

    <fragment
        android:id="@+id/BookDetailsFragment"
        android:name="com.cellodove.presentation.ui.detail.DetailFragment"
        tools:layout="@layout/fragment_detail">

    </fragment>
</navigation>
```

![kakao_image5.png](/image/kakao_image5.png)

네비게이션에서 액션을 사용해 화면을 이동합니다.

### Search

- BookViewHolder

```kotlin
class BookViewHolder(private val binding:BookListItemBinding) :  RecyclerView.ViewHolder(binding.root){

    fun bind(documents : Documents, searchWord : String, isLikeList : ArrayList<String>){
        showData(documents,searchWord,isLikeList)
    }

    private fun showData(documents : Documents, searchWord : String, isLikeList : ArrayList<String>){
        binding.apply {
            if (documents.thumbnail.isNotEmpty()){
                Glide.with(binding.root)
                    .load(documents.thumbnail)
                    .override(100,100)
                    .into(binding.ImageThumbnail)
            }else{
                binding.ImageThumbnail.setImageResource(R.drawable.ic_baseline_image_not_supported_24)
            }
            val decimal = DecimalFormat("#,###")
            bookPrice.text = "${decimal.format(documents.price)}원"

            var text = documents.title
            text = text.replace(searchWord, "<font color='#0be3d8'>$searchWord</font>")
            bookName.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.likeButton.isChecked = isLikeList.contains(documents.title)
        }
    }

    companion object{
        fun create(parent: ViewGroup): BookViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            return BookViewHolder(BookListItemBinding.inflate(layoutInflater,parent,false))
        }
    }
}
```

리사이클러뷰 표출을위해 먼저 뷰홀더를 만들어주었습니다. 뷰모델에서 좋아요리스트를 받아 뷰홀더로 보내줍니다. 뷰홀더에 입력된 `documents.title` 와 비교하여 좋아요 리스트에 해당 이름이있으면 좋아요 버튼이 활성화되게 하였습니다. 그리고 검색어와 같은 텍스트가 있으면 해당 글자의 색상이 변경되도록 만들었습니다.

### SearchAdapter

```kotlin
class SearchAdapter : PagingDataAdapter<Documents,BookViewHolder>(DOCUMENTS_COMPARATOR) {
    private val likeList  = arrayListOf<String>()
    private var searchWord : String = ""
    private lateinit var itemClickListener : OnItemClickListener

    interface OnItemClickListener {
        fun onClick(documents : Documents)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    fun setSearchWord(word : String){
        this.searchWord = word
    }

    fun setCheckLike(likeList : ArrayList<String>){
        this.likeList.clear()
        this.likeList.addAll(likeList)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null){
            holder.bind(item,searchWord,likeList)
            holder.itemView.setOnClickListener {
                itemClickListener.onClick(getItem(position)!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder.create(parent)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

		companion object{
        private val DOCUMENTS_COMPARATOR = object : DiffUtil.ItemCallback<Documents>(){
            override fun areItemsTheSame(oldItem: Documents, newItem: Documents) = oldItem.isbn == newItem.isbn && oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Documents, newItem: Documents) = oldItem == newItem
        }
    }
}
```

어댑터에 클릭리스너를 추가해주었습니다.

### SearchFragment

```kotlin

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {
    private val viewModel : MainViewModel by activityViewModels()
    private val searchAdapter = SearchAdapter()
    private var searchJob: Job? = null
    private var oldQuery = ""
    private var newQuery = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.layoutToolbar,"Pay", leftIcon = R.drawable.ic_baseline_favorite_24)
        viewInit()
    }

    override fun onResume() {
        super.onResume()
        searchAdapter.setCheckLike(viewModel.getCheckLikeList())
    }

    override fun observeViewModel() {
        statusList()
    }

    private fun viewInit(){
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.searchRecycler.adapter = searchAdapter
        binding.searchRecycler.addItemDecoration(decoration)

        binding.etQuery.setOnEditorActionListener { _, actionId, _ ->
            newQuery = binding.etQuery.text.toString()
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                when {
                    binding.etQuery.text.toString().isEmpty() -> {
                        binding.inputLayout.error = "텍스트를 입력해 주세요."
                    }
                    newQuery == oldQuery -> Unit
                    else -> {
                        oldQuery = newQuery
                        hideKeyboard()
                        binding.inputLayout.error = null
                        viewModel.deleteAllLikeList()
                        searchBook(binding.etQuery.text.toString())
                        binding.searchRecycler.scrollToPosition(0)
                        searchAdapter.setSearchWord(binding.etQuery.text.toString())
                    }
                }
                true
            } else {
                false
            }
        }

        binding.etQuery.doOnTextChanged { _, _, _, _ ->
            binding.inputLayout.error = null
        }

        binding.inputLayout.setEndIconOnClickListener {
            newQuery = binding.etQuery.text.toString()
            when {
                binding.etQuery.text.toString().isEmpty() -> {
                    binding.inputLayout.error = "텍스트를 입력해 주세요."
                }
                newQuery == oldQuery -> Unit
                else -> {
                    oldQuery = newQuery
                    hideKeyboard()
                    binding.inputLayout.error = null
                    viewModel.deleteAllLikeList()
                    searchBook(binding.etQuery.text.toString())
                    binding.searchRecycler.scrollToPosition(0)
                    searchAdapter.setSearchWord(binding.etQuery.text.toString())
                }
            }
        }

				searchAdapter.setItemClickListener(object : SearchAdapter.OnItemClickListener{
            override fun onClick(documents: Documents) {
                val bundle = Bundle()
                bundle.putString(TITLE, documents.title)
                bundle.putString(THUMBNAIL, documents.thumbnail)
                bundle.putInt(PRICE, documents.price)
                bundle.putString(PUBLISHER, documents.publisher)
                bundle.putString(CONTENTS, documents.contents)
                findNavController().navigate(R.id.BookDetailsFragment,bundle)
            }
        })

        binding.retryButton.setOnClickListener {
            searchAdapter.retry()
        }
    }

    private fun searchBook(query :String){
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchBook(query).collectLatest {
                binding.searchNothing.isVisible = false
                binding.errorLayout.isVisible = false
                binding.searchRecycler.visibility = View.VISIBLE
                searchAdapter.setCheckLike(viewModel.getCheckLikeList())
                searchAdapter.submitData(it)
            }
        }
    }

    private fun statusList() {
        searchAdapter.addLoadStateListener { loadState ->
            loadState.decideOnState(
                adapter = searchAdapter,
                showLoading = { visible ->
                    binding.progressBar.isVisible = visible
                    binding.searchNothing.isVisible = false
                    binding.errorLayout.isVisible = false
                },
                showEmptyState = { visible ->
                    binding.searchNothing.isVisible = visible
                    binding.errorLayout.isVisible = false
                },
                showError = {
                    binding.searchRecycler.visibility = View.INVISIBLE
                    binding.searchNothing.isVisible = false
                    binding.errorLayout.isVisible = true
                }
            )
        }
    }

    private fun hideKeyboard(){
        (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(binding.etQuery.windowToken, 0)
    }
}
```

검색 버튼을 따로 추가하지않고 `TextInputLayout` 에서 아이콘을 추가해 버튼처럼 동작하도록 하였습니다.

api를 중복으로 호출하는 상황을 막기위해 검색어가 같다면 api를 호출하지 않도록 만들었습니다.

검색어가없으면 검색되지 않도록 만들었습니다.

리사이클러뷰 상태에따라 UI가 변경되도록 만들었습니다.

리스트를 클릭하면 해당 데이터를 번들로만들어 상세화면으로 넘깁니다.

### Detail

```kotlin
@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {
    private val viewModel : MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = arguments?.getString(TITLE) ?: ""
        val isLike = viewModel.getCheckLikeList().contains(title)
        val thumbnail = arguments?.getString(THUMBNAIL) ?: ""
        val price = arguments?.getInt(PRICE) ?: 0
        val publisher = arguments?.getString(PUBLISHER) ?: ""
        val contents = arguments?.getString(CONTENTS) ?: ""

        initToolbar(
            binding.layoutToolbar,
            "도서 상세",
            leftIcon = R.drawable.ic_baseline_keyboard_arrow_left_30,
            leftClick = {
                requireActivity().onBackPressed()
            }
        )

        binding.apply {
            if (thumbnail.isNotEmpty()){
                Glide.with(binding.root)
                    .load(thumbnail)
                    .override(200,200)
                    .into(binding.thumbnail)
            }else{
                binding.thumbnail.setImageResource(R.drawable.ic_baseline_image_not_supported_200)
            }
            bookName.text = title
            val decimal = DecimalFormat("#,###")
            bookPrice.text = "${decimal.format(price)}원"
            bookPublisher.text = publisher
            bookContent.text = contents
            likeButton.isChecked = isLike
            likeButton.setOnClickListener {
                if (likeButton.isChecked){
                    viewModel.addCheckLike(title)
                }else{
                    viewModel.deleteCheckLike(title)
                }
            }
        }
    }

    override fun observeViewModel() = Unit
}
```

번들로 받은 데이터를 처리해 화면에 표출합니다.

### Util

- ValueExtension

```kotlin
fun CombinedLoadStates.decideOnState(
    adapter : PagingDataAdapter<Documents, BookViewHolder>,
    showLoading: (Boolean) -> Unit,
    showEmptyState: (Boolean) -> Unit,
    showError: () -> Unit
) {
    showLoading(refresh is LoadState.Loading)

    showEmptyState(
        source.append is LoadState.NotLoading
                && source.append.endOfPaginationReached
                && adapter.itemCount == 0
    )

    val errorState = source.append as? LoadState.Error
        ?: source.prepend as? LoadState.Error
        ?: source.refresh as? LoadState.Error
        ?: append as? LoadState.Error
        ?: prepend as? LoadState.Error
        ?: refresh as? LoadState.Error

    errorState?.let { showError() }
}
```

데이터를 처리하기위한 코드들입니다. 여기서는 페이징 상태를 처리하기위해 따로 빼 두었습니다.

- ViewExtension

```kotlin
fun initToolbar(
    toolbar: LayoutToolbarBinding,
    title: String? = null,
    @DrawableRes leftIcon: Int? = null,
    leftClick: (() -> Unit)? = null,
) {
    toolbar.apply {
        tvTitle.text = title
        ivLeft.setImageResource(leftIcon ?: 0)
        ivLeft.visibility = if (leftIcon == null) View.GONE else View.VISIBLE
        ivLeft.setOnClickListener {
            leftClick?.invoke()
        }
        tvLeft.setOnClickListener {
            leftClick?.invoke()
        }
    }
}
```

뷰익스텐션 코드입니다. 여기서는 툴바 UI처리를위한 코드만 있습니다.

## App

App모듈에서는 힐트 설정만 해줍니다.

- App

```kotlin
@HiltAndroidApp
class App : Application()
```

힐트를 초기화 해줍니다.

힐트 모듈을 설정해줍니다.

- UseCaseModule

```kotlin
@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun providesSearchBookPagingUseCase(repository: BookRepository) : SearchBookPagingUseCase {
        return SearchBookPagingUseCase(repository)
    }
}
```

- DataSourceModule

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun providesBookRemotePagingDataSource(source: BookRemotePagingDataSource): BookRemotePagingDataSource {
        return source
    }
}
```

- RepositoryModule

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesBookRepository(repository: BookRepositoryImpl): BookRepository {
        return repository
    }
}
```

- NetworkModule

```kotlin
@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    private const val BASE_URL = "https://dapi.kakao.com"

    @Provides
    @Singleton
    fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(getHttpClient())
            .build()
    }

    @Provides
    @Singleton
    fun getHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            readTimeout(10, TimeUnit.SECONDS)
            connectTimeout(10, TimeUnit.SECONDS)
            writeTimeout(15, TimeUnit.SECONDS)
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build()
    }

    @Provides
    @Singleton
    fun provideDeliveryService(retrofit: Retrofit): KakaoBookService {
        return retrofit.create(KakaoBookService::class.java)
    }
}
```

실행 화면입니다.

![kakao_image6.jpg](/image/kakao_image6.gif)