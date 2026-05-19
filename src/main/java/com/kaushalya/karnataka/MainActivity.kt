package com.kaushalya.karnataka

import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

@Entity(tableName = "workers")
data class Worker(
    @PrimaryKey(autoGenerate = true) val workerId: Long = 0,
    val name: String,
    val phone: String,
    val email: String? = null,
    val profileImage: String? = null,
    val bio: String? = null,
    val location: String,
    val primaryCategory: String,
    val createdAt: Long = System.currentTimeMillis(),
    val isActive: Boolean = true
)

@Entity(tableName = "services")
data class WorkerService(
    @PrimaryKey(autoGenerate = true) val serviceId: Long = 0,
    val workerId: Long,
    val category: String,
    val serviceName: String,
    val description: String? = null,
    val priceType: String,
    val price: Double,
    val createdAt: Long = System.currentTimeMillis(),
    val isActive: Boolean = true
)

@Entity(tableName = "portfolio_items")
data class PortfolioItem(
    @PrimaryKey(autoGenerate = true) val portfolioId: Long = 0,
    val workerId: Long,
    val imageUri: String,
    val title: String? = null,
    val description: String? = null,
    val serviceCategory: String? = null,
    val uploadedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "reviews")
data class Review(
    @PrimaryKey(autoGenerate = true) val reviewId: Long = 0,
    val workerId: Long,
    val customerName: String,
    val rating: Int,
    val reviewText: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "hire_requests")
data class HireRequest(
    @PrimaryKey(autoGenerate = true) val requestId: Long = 0,
    val workerId: Long,
    val customerName: String,
    val customerPhone: String,
    val serviceRequested: String,
    val message: String? = null,
    val status: String = "PENDING",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true) val categoryId: Long = 0,
    val categoryName: String,
    val iconResource: String? = null,
    val displayOrder: Int
)

@Dao
interface WorkerDao {
    @Insert suspend fun insert(worker: Worker): Long
    @Update suspend fun update(worker: Worker)
    @Query("SELECT * FROM workers WHERE workerId = :id") fun observeWorker(id: Long): Flow<Worker?>
    @Query("SELECT * FROM workers WHERE isActive = 1 ORDER BY createdAt DESC") fun observeWorkers(): Flow<List<Worker>>
    @Query("SELECT COUNT(*) FROM workers") suspend fun countWorkers(): Int
}

@Dao
interface ServiceDao {
    @Insert suspend fun insert(service: WorkerService): Long
    @Update suspend fun update(service: WorkerService)
    @Delete suspend fun delete(service: WorkerService)
    @Query("SELECT * FROM services WHERE workerId = :workerId ORDER BY createdAt DESC") fun observeByWorker(workerId: Long): Flow<List<WorkerService>>
    @Query("SELECT * FROM services ORDER BY createdAt DESC") fun observeAll(): Flow<List<WorkerService>>
}

@Dao
interface PortfolioDao {
    @Insert suspend fun insert(item: PortfolioItem): Long
    @Delete suspend fun delete(item: PortfolioItem)
    @Query("SELECT * FROM portfolio_items WHERE workerId = :workerId ORDER BY uploadedAt DESC") fun observeByWorker(workerId: Long): Flow<List<PortfolioItem>>
}

@Dao
interface ReviewDao {
    @Insert suspend fun insert(review: Review): Long
    @Query("SELECT * FROM reviews WHERE workerId = :workerId ORDER BY createdAt DESC") fun observeByWorker(workerId: Long): Flow<List<Review>>
    @Query("SELECT * FROM reviews ORDER BY createdAt DESC") fun observeAll(): Flow<List<Review>>
}

@Dao
interface HireRequestDao {
    @Insert suspend fun insert(request: HireRequest): Long
    @Update suspend fun update(request: HireRequest)
    @Delete suspend fun delete(request: HireRequest)
    @Query("SELECT * FROM hire_requests WHERE workerId = :workerId ORDER BY createdAt DESC") fun observeByWorker(workerId: Long): Flow<List<HireRequest>>
}

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE) suspend fun insert(category: Category)
    @Query("SELECT * FROM categories ORDER BY displayOrder") fun observeAll(): Flow<List<Category>>
    @Query("SELECT categoryName FROM categories") suspend fun getCategoryNames(): List<String>
    @Query("SELECT COUNT(*) FROM categories") suspend fun count(): Int
}

@Database(
    entities = [Worker::class, WorkerService::class, PortfolioItem::class, Review::class, HireRequest::class, Category::class],
    version = 1,
    exportSchema = false
)
abstract class KaushalyaDatabase : RoomDatabase() {
    abstract fun workerDao(): WorkerDao
    abstract fun serviceDao(): ServiceDao
    abstract fun portfolioDao(): PortfolioDao
    abstract fun reviewDao(): ReviewDao
    abstract fun hireRequestDao(): HireRequestDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile private var instance: KaushalyaDatabase? = null
        fun get(context: Context): KaushalyaDatabase = instance ?: synchronized(this) {
            Room.databaseBuilder(context.applicationContext, KaushalyaDatabase::class.java, "kaushalya_database")
                .fallbackToDestructiveMigration(false)
                .build()
                .also { instance = it }
        }
    }
}

data class WorkerWithStats(
    val worker: Worker,
    val avgRating: Double,
    val reviewCount: Int,
    val minPrice: Double
)

class KaushalyaViewModel(app: Application) : AndroidViewModel(app) {
    private val db = KaushalyaDatabase.get(app)
    private val prefs = app.getSharedPreferences("kaushalya_prefs", Context.MODE_PRIVATE)
    val language = MutableStateFlow(prefs.getString("lang", "en") ?: "en")
    private val _currentWorkerId = MutableStateFlow(prefs.getLong("current_worker_id", -1L))

    val firstLaunch = MutableStateFlow(prefs.getBoolean("first_launch", true))
    val isWorker = MutableStateFlow(prefs.getBoolean("is_worker", false))
    val search = MutableStateFlow("")
    val selectedCategory = MutableStateFlow<String?>(null)
    val minRating = MutableStateFlow(0f)
    val priceRange = MutableStateFlow(0f..10000f)
    val sortBy = MutableStateFlow("RATING")

    val categories = db.categoryDao().observeAll().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val services = _currentWorkerId.flatMapLatest { id -> if (id > 0) db.serviceDao().observeByWorker(id) else flowOf(emptyList()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val portfolio = _currentWorkerId.flatMapLatest { id -> if (id > 0) db.portfolioDao().observeByWorker(id) else flowOf(emptyList()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val reviews = _currentWorkerId.flatMapLatest { id -> if (id > 0) db.reviewDao().observeByWorker(id) else flowOf(emptyList()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val requests = _currentWorkerId.flatMapLatest { id -> if (id > 0) db.hireRequestDao().observeByWorker(id) else flowOf(emptyList()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val currentWorker = _currentWorkerId.flatMapLatest { id -> if (id > 0) db.workerDao().observeWorker(id) else flowOf(null) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val workersWithStats = combine(
        db.workerDao().observeWorkers(),
        db.serviceDao().observeAll(),
        db.reviewDao().observeAll(),
        search,
        selectedCategory,
        minRating,
        priceRange,
        sortBy
    ) { values ->
        val workers = values[0] as List<Worker>
        val allServices = values[1] as List<WorkerService>
        val allReviews = values[2] as List<Review>
        val query = values[3] as String
        val category = values[4] as String?
        val rating = values[5] as Float
        val prices = values[6] as ClosedFloatingPointRange<Float>
        val sort = values[7] as String
        workers.map { worker ->
            val workerReviews = allReviews.filter { it.workerId == worker.workerId }
            val workerServices = allServices.filter { it.workerId == worker.workerId && it.isActive }
            WorkerWithStats(
                worker = worker,
                avgRating = workerReviews.map { it.rating }.average().takeIf { !it.isNaN() } ?: 0.0,
                reviewCount = workerReviews.size,
                minPrice = workerServices.minOfOrNull { it.price } ?: 0.0
            )
        }.filter {
            val matchesSearch = query.isBlank() || it.worker.name.contains(query, true) || it.worker.location.contains(query, true)
            val matchesCategory = category == null || it.worker.primaryCategory == category
            val matchesPrice = it.minPrice == 0.0 || it.minPrice in prices.start.toDouble()..prices.endInclusive.toDouble()
            matchesSearch && matchesCategory && matchesPrice && it.avgRating >= rating
        }.let { list ->
            when (sort) {
                "REVIEWS" -> list.sortedByDescending { it.reviewCount }
                "PRICE" -> list.sortedBy { it.minPrice }
                else -> list.sortedWith(compareByDescending<WorkerWithStats> { it.avgRating }.thenBy { it.minPrice })
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch(Dispatchers.IO) { seedIfNeeded() }
    }

    fun setLanguage(lang: String) {
        prefs.edit().putString("lang", lang).apply()
        language.value = lang
    }

    private suspend fun seedIfNeeded() {
        val names = listOf(
            "Electrician",
            "Plumber",
            "Carpenter",
            "Painter",
            "Mason",
            "AC Technician",
            "Appliance Repair",
            "Gardener",
            "Fence Work",
            "Coconut Business",
            "Pit Digging Work",
            "Poultry Business"
        )
        val existingNames = db.categoryDao().getCategoryNames().toSet()
        names.filterNot { it in existingNames }.forEachIndexed { index, name ->
            db.categoryDao().insert(Category(categoryName = name, displayOrder = existingNames.size + index + 1))
        }
        if (db.workerDao().countWorkers() == 0) {
            val ramesh = db.workerDao().insert(Worker(name = "Ramesh Kumar", phone = "9876543210", email = "ramesh@email.com", location = "Jayanagar, Bengaluru", primaryCategory = "Electrician", bio = "15 years of electrical repair, wiring, fan fitting and switch board work."))
            val suresh = db.workerDao().insert(Worker(name = "Suresh Patil", phone = "9123456789", location = "Hubballi Town", primaryCategory = "Plumber", bio = "Fast leakage repairs, bathroom fittings and water motor service."))
            val kumar = db.workerDao().insert(Worker(name = "Kumar Chari", phone = "9988776655", location = "Mysuru", primaryCategory = "Carpenter", bio = "Custom cabinets, doors and repair work for homes and shops."))
            listOf(
                WorkerService(workerId = ramesh, category = "Electrician", serviceName = "Fan Repair", description = "Ceiling fan, regulator and wiring check.", priceType = "FIXED", price = 200.0),
                WorkerService(workerId = ramesh, category = "Electrician", serviceName = "House Wiring", description = "New house and renovation wiring.", priceType = "STARTING_AT", price = 5000.0),
                WorkerService(workerId = suresh, category = "Plumber", serviceName = "Tap Leakage Fix", description = "Kitchen and bathroom tap leakage.", priceType = "FIXED", price = 300.0),
                WorkerService(workerId = kumar, category = "Carpenter", serviceName = "Cabinet Making", description = "Kitchen and bedroom cabinet work.", priceType = "STARTING_AT", price = 8000.0)
            ).forEach { db.serviceDao().insert(it) }
            db.reviewDao().insert(Review(workerId = ramesh, customerName = "Rajesh", rating = 5, reviewText = "Clean work and came on time. Fan is running perfectly."))
            db.reviewDao().insert(Review(workerId = ramesh, customerName = "Priya", rating = 4, reviewText = "Good wiring work and fair price."))
            db.reviewDao().insert(Review(workerId = suresh, customerName = "Anitha", rating = 5, reviewText = "Solved water leakage quickly."))
            db.reviewDao().insert(Review(workerId = kumar, customerName = "Mohan", rating = 4, reviewText = "Strong cabinet work and neat finish."))
        }
    }

    fun finishOnboarding(workerMode: Boolean) {
        prefs.edit().putBoolean("first_launch", false).putBoolean("is_worker", workerMode).apply()
        firstLaunch.value = false
        isWorker.value = workerMode
    }

    fun registerWorker(worker: Worker) = viewModelScope.launch {
        val id = withContext(Dispatchers.IO) { db.workerDao().insert(worker) }
        prefs.edit().putLong("current_worker_id", id).putBoolean("is_worker", true).putBoolean("first_launch", false).apply()
        _currentWorkerId.value = id
        isWorker.value = true
        firstLaunch.value = false
    }

    fun updateWorker(worker: Worker) = viewModelScope.launch(Dispatchers.IO) { db.workerDao().update(worker) }
    fun saveService(service: WorkerService) = viewModelScope.launch(Dispatchers.IO) { if (service.serviceId == 0L) db.serviceDao().insert(service) else db.serviceDao().update(service) }
    fun deleteService(service: WorkerService) = viewModelScope.launch(Dispatchers.IO) { db.serviceDao().delete(service) }
    fun addPortfolio(item: PortfolioItem) = viewModelScope.launch(Dispatchers.IO) { db.portfolioDao().insert(item) }
    fun deletePortfolio(item: PortfolioItem) = viewModelScope.launch(Dispatchers.IO) {
        db.portfolioDao().delete(item)
        item.imageUri.takeIf { it.startsWith("/") }?.let { File(it).delete() }
    }
    fun addReview(review: Review) = viewModelScope.launch(Dispatchers.IO) { db.reviewDao().insert(review) }
    fun addHireRequest(request: HireRequest) = viewModelScope.launch(Dispatchers.IO) { db.hireRequestDao().insert(request) }
    fun updateRequest(request: HireRequest) = viewModelScope.launch(Dispatchers.IO) { db.hireRequestDao().update(request) }
    fun deleteRequest(request: HireRequest) = viewModelScope.launch(Dispatchers.IO) { db.hireRequestDao().delete(request) }
    fun setCurrentWorker(id: Long) {
        prefs.edit().putLong("current_worker_id", id).apply()
        _currentWorkerId.value = id
    }
    fun logout() {
        prefs.edit().clear().apply()
        firstLaunch.value = true
        isWorker.value = false
        _currentWorkerId.value = -1L
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val vm = remember {
                ViewModelProvider(
                    this,
                    ViewModelProvider.AndroidViewModelFactory.getInstance(application)
                )[KaushalyaViewModel::class.java]
            }
            KaushalyaTheme { KaushalyaApp(vm) }
        }
    }
}

@Composable
fun KaushalyaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = androidx.compose.material3.lightColorScheme(
            primary = Color(0xFF1976D2),
            secondary = Color(0xFFFFA726),
            tertiary = Color(0xFF2E7D32),
            background = Color(0xFFF7F8FA),
            surface = Color.White
        ),
        content = content
    )
}

val LocalLang = compositionLocalOf { "en" }
val LocalToggleLanguage = compositionLocalOf<() -> Unit> { {} }

object Translations {
    val map = mapOf(
        "Kaushalya-Karnataka" to "Kaushalya-Karnataka",
        "Your Local Skills Marketplace" to "ನಿಮ್ಮ ಸ್ಥಳೀಯ ಕೌಶಲ್ಯ ಮಾರುಕಟ್ಟೆ",
        "Welcome" to "ಸ್ವಾಗತ",
        "Skip" to "ಬಿಟ್ಟುಹಾಕಿ",
        "Choose Your Role" to "ನಿಮ್ಮ ಪಾತ್ರವನ್ನು ಆಯ್ಕೆಮಾಡಿ",
        "I'm a Worker" to "ನಾನು ಕೆಲಸಗಾರ",
        "Showcase your skills, service cards and portfolio" to "ನಿಮ್ಮ ಕೌಶಲ್ಯಗಳನ್ನು, ಸೇವಾ ವಿವರ ಮತ್ತು ಪೋರ್ಟ್ಫೋಲಿಯೊ ಪ್ರದರ್ಶಿಸಿ",
        "I'm a Customer" to "ನಾನು ಗ್ರಾಹಕ",
        "Find trusted skilled workers nearby" to "ನಿಮ್ಮ ಬಳಿಯ ನಂಬಬಹುದಾದ ಕೌಶಲ್ಯಗಾರರನ್ನು ಹುಡುಕಿ",
        "Create Worker Profile" to "ಕೆಲಸದ ಪ್ರೊಫೈಲ್ ರಚಿಸಿ",
        "Add photo from gallery" to "ಗ್ಯಾಲರಿಯಿಂದ ಫೋಟೋ ಸೇರಿಸಿ",
        "Full Name *" to "ಪೂರ್ಣ ಹೆಸರು *",
        "Phone Number *" to "ದೂರವಾಣಿ ಸಂಖ್ಯೆ *",
        "Email" to "ಇಮೇಲ್",
        "Location / Area *" to "ಸ್ಥಳ / ಪ್ರದೇಶ *",
        "About Me" to "ನನ್ನ ಬಗ್ಗೆ",
        "Create Profile" to "ಪ್ರೊಫೈಲ್ ರಚಿಸಿ",
        "Save Profile" to "ಪ್ರೊಫೈಲ್ ಉಳಿಸಿ",
        "Cancel" to "ರದ್ದುಗೊಳಿಸು",
        "Name must be at least 3 characters." to "ಹೆಸರು ಕನಿಷ್ಠ 3 ಅಕ್ಷರಗಳು ಇರಬೇಕು.",
        "Phone number must be 10 digits." to "ದೂರವಾಣಿ ಸಂಖ್ಯೆ 10 ಅಂಕೆಗಳಿರಬೇಕು.",
        "Enter a valid email address." to "ಮಾನ್ಯವಾದ ಇಮೇಲ್ ವಿಳಾಸವನ್ನು ನಮೂದಿಸಿ.",
        "Select a primary category." to "ಪ್ರಾಥಮಿಕ ವರ್ಗವನ್ನು ಆಯ್ಕೆಮಾಡಿ.",
        "Welcome back, %s!" to "%s, ಸ್ವಾಗತ!",
        "Worker" to "ಕೆಲಸದವರು",
        "Services" to "ಸೇವೆಗಳು",
        "Rating" to "ರೇಟಿಂಗ್",
        "Reviews" to "ವಿಮರ್ಶೆಗಳು",
        "Pending" to "ಬಾಕಿ",
        "Quick Actions" to "ತ್ವರಿತ ಕ್ರಿಯೆಗಳು",
        "Add Service" to "ಸೇವೆ ಸೇರಿಸಿ",
        "View Portfolio" to "ಪೋರ್ಟ್ಫೋಲಿಯೊ ನೋಡಿ",
        "Manage Profile" to "ಪ್ರೊಫೈಲ್ ನಿರ್ವಹಣೆ",
        "Home" to "ಮುಖಪುಟ",
        "Portfolio" to "ಪೋರ್ಟ್ಫೋಲಿಯೊ",
        "Requests" to "ವಿನಂತಿಗಳು",
        "Profile" to "ಪ್ರೊಫೈಲ್",
        "My Services" to "ನನ್ನ ಸೇವೆಗಳು",
        "Add" to "ಸೇರಿಸಿ",
        "Search services" to "ಸೇವೆಗಳನ್ನು ಹುಡುಕಿ",
        "All" to "ಎಲ್ಲಾ",
        "Add New Service" to "ಹೊಸ ಸೇವೆ ಸೇರಿಸಿ",
        "Edit Service" to "ಸೇವೆಯನ್ನು ಸಂಪಾದಿಸಿ",
        "Service Name *" to "ಸೇವೆಯ ಹೆಸರು *",
        "Description" to "ವಿವರಣೆ",
        "Price Type *" to "ಬೆಲೆಯ ತರಹ *",
        "Fixed Price" to "ನಿಗದಿತ ಬೆಲೆ",
        "Starting At" to "ಪ್ರಾರಂಭ ದರ",
        "Price *" to "ಬೆಲೆ *",
        "Rs" to "₹",
        "Select a category." to "ವರ್ಗವನ್ನು ಆಯ್ಕೆಮಾಡಿ.",
        "Service name must be at least 3 characters." to "ಸೇವೆಯ ಹೆಸರಿನಲ್ಲಿ ಕನಿಷ್ಠ 3 ಅಕ್ಷರಗಳಿರಬೇಕು.",
        "Enter a positive price." to "ಧನಾತ್ಮಕ ಬೆಲೆ ನಮೂದಿಸಿ.",
        "Save Service" to "ಸೇವೆಯನ್ನು ಉಳಿಸಿ",
        "Delete Service" to "ಸೇವೆಯನ್ನು ಅಳಿಸಿ",
        "My Portfolio" to "ನನ್ನ ಪೋರ್ಟ್ಫೋಲಿಯೊ",
        "Add Work" to "ಕೆಲಸ ಸೇರಿಸಿ",
        "Upload your work photos to build community trust." to "ಸಮುದಾಯದ ನಂಬಿಕೆಯನ್ನು ನಿರ್ಮಿಸಲು ನಿಮ್ಮ ಕೆಲಸದ ಛಾಯಾಚಿತ್ರಗಳನ್ನು ಅಪ್‌ಲೋಡ್ ಮಾಡಿ.",
        "No image selected" to "ಚಿತ್ರ ಆಯ್ಕೆ ಮಾಡಿಲ್ಲ",
        "Choose Photo From Gallery" to "ಗ್ಯಾಲರಿಯಿಂದ ಫೋಟೋ ಆಯ್ಕೆಮಾಡಿ",
        "Work Title" to "ಕಾರ್ಯದ ಶೀರ್ಷಿಕೆ",
        "Upload" to "ಅಪ್‌ಲೋಡ್",
        "Select an image first." to "ಮೊದಲು ಚಿತ್ರವನ್ನು ಆಯ್ಕೆಮಾಡಿ.",
        "Hire Requests" to "ನೇಮಕಾತಿ ವಿನಂತಿಗಳು",
        "PENDING" to "ಬಾಕಿ",
        "ACCEPTED" to "ಸ್ವೀಕೃತ",
        "COMPLETED" to "ಪೂರ್ಣಗೊಂಡಿತು",
        "Request Service" to "ಸೇವೆಯನ್ನು ವಿನಂತಿ ಮಾಡಿ",
        "To: %s" to "ಗೆ: %s",
        "Your Name *" to "ನಿಮ್ಮ ಹೆಸರು *",
        "Your Phone *" to "ನಿಮ್ಮ ಫೋನ್ *",
        "Message" to "ಸಂದೇಶ",
        "Send" to "ಕಳುಹಿಸಿ",
        "Write a Review" to "ವಿಮರ್ಶೆ ಬರೆಯಿರಿ",
        "For: %s" to "ಗಾಗಿ: %s",
        "Your Review *" to "ನಿಮ್ಮ ವಿಮರ್ಶೆ *",
        "Select a rating." to "ರೇಟಿಂಗ್ ಆಯ್ಕೆಮಾಡಿ.",
        "Review must be at least 10 characters." to "ವಿಮರ್ಶೆ ಕನಿಷ್ಠ 10 ಅಕ್ಷರಗಳಿರಬೇಕು.",
        "Submit" to "ಸಲ್ಲಿಸು",
        "Member Since: %s" to "ಸದಸ್ಯತ್ವ: %s",
        "%d Services" to "%d ಸೇವೆಗಳು",
        "%.1f Avg Rating" to "%.1f ಸರಾಸರಿ ರೇಟಿಂಗ್",
        "%d Reviews" to "%d ವಿಮರ್ಶೆಗಳು",
        "Edit Profile" to "ಪ್ರೊಫೈಲ್ ಸಂಪಾದನೆ",
        "View Public Profile" to "ಸಾರ್ವಜನಿಕ ಪ್ರೊಫೈಲ್ ನೋಡಿ",
        "Find skilled workers in your area" to "ನಿಮ್ಮ ಪ್ರದೇಶದಲ್ಲಿ ಕೌಶಲ್ಯವಂತರನ್ನು ಹುಡುಕಿ",
        "Search workers" to "ಕೆಲಸದವರನ್ನು ಹುಡುಕಿ",
        "Filter Workers" to "ಕೆಲಸದವರನ್ನು ಫಿಲ್ಟರ್ ಮಾಡಿ",
        "Minimum Rating: %d" to "ಕನಿಷ್ಠ ರೇಟಿಂಗ್: %d",
        "Price Range: Rs %d - Rs %d" to "ಬೆಲೆಯ ಶ್ರೇಣಿ: ₹%d - ₹%d",
        "Sort By" to "ಕ್ರಮಗೊಳಿಸಿ",
        "Rating (High to Low)" to "ರೇಟಿಂಗ್ (ಹೆಚ್ಚಿನಿಂದ ಕಡಿಮೆ)",
        "Most Reviewed" to "ಅಧಿಕ ವಿಮರ್ಶೆ ಪಡೆದಿವೆ",
        "Price (Low to High)" to "ಬೆಲೆ (ಕಡಿಮೆ → ಹೆಚ್ಚು)",
        "Apply Filters" to "ಫಿಲ್ಟರ್ ಅನ್ವಯಿಸಿ",
        "Reset" to "ಮರುಹೊಂದಿಸಿ",
        "From Rs %d" to "₹%d ರಿಂದ",
        "View" to "ನೋಡಿರಿ",
        "Call" to "ಕರೆ",
        "Hire Me" to "ನನ್ನನ್ನು ನೇಮಿಸಿ",
        "Review" to "ವಿಮರ್ಶೆ",
        "Settings" to "ಸೆಟ್ಟಿಂಗ್ಸ್",
        "Account Settings" to "ಖಾತೆ ಸೆಟ್ಟಿಂಗ್ಸ್",
        "Notifications" to "ಅಧಿಸೂಚನೆಗಳು",
        "Privacy" to "ಗೌಪ್ಯತೆ",
        "Language: English" to "ಭಾಷೆ: ಇಂಗ್ಲಿಷ್",
        "About App" to "ಆಪ್ ಬಗ್ಗೆ",
        "Help & Support" to "ಸಹಾಯ ಮತ್ತು ಬೆಂಬಲ",
        "Logout" to "ಲಾಗ್ ಔಟ್",
        "Edit" to "ಸಂಪಾದಿಸಿ",
        "Delete" to "ಅಳಿಸಿ",
        "Phone: %s" to "ಫೋನ್: %s",
        "Service: %s" to "ಸೇವೆ: %s",
        "Email: %s" to "ಇಮೇಲ್: %s",
        "Location: %s" to "ಸ್ಥಳ: %s",
        "Primary: %s" to "ಪ್ರಾಥಮಿಕ: %s",
        "About: %s" to "ಬಗ್ಗೆ: %s",
        "Accept" to "ಸ್ವೀಕರಿಸಿ",
        "Reject" to "ನಿರಾಕರಿಸಿ",
        "Complete" to "ಪೂರ್ಣಗೊಳಿಸಿ",
        "Just now" to "ಈಗಷ್ಟೇ",
        "%d min ago" to "%d ನಿಮಿಷದ ಹಿಂದೆ",
        "%d hours ago" to "%d ಗಂಟೆಯ ಹಿಂದೆ",
        "Add Portfolio Item" to "ಪೋರ್ಟ್ಫೋಲಿಯೊ ಸೇರಿಸಿ",
        "Work photo" to "ಕೆಲಸದ ಫೋಟೋ",
        "My Profile" to "ನನ್ನ ಪ್ರೊಫೈಲ್",
        "Rating (High to Low)" to "ರೇಟಿಂಗ್ (ಹೆಚ್ಚಿನಿಂದ ಕಡಿಮೆ)",
        "Price (Low to High)" to "ಬೆಲೆ (ಕಡಿಮೆಯಿಂದ ಹೆಚ್ಚು)",
        "Language" to "ಭಾಷೆ",
        "English" to "English",
        "Kannada" to "ಕನ್ನಡ"
    )
}


@Composable
fun t(key: String, vararg args: Any?): String {
    val lang = LocalLang.current
    val text = if (lang == "kn") cleanKannada(key) ?: key else key
    return if (args.isNotEmpty()) try { String.format(text, *args) } catch (e: Exception) { text } else text
}

fun cleanKannada(key: String): String? = when (key) {
    "Your Local Skills Marketplace" -> "\u0ca8\u0cbf\u0cae\u0ccd\u0cae \u0cb8\u0ccd\u0ca5\u0cb3\u0cc0\u0caf \u0c95\u0ccc\u0cb6\u0cb2\u0ccd\u0caf \u0cae\u0cbe\u0cb0\u0cc1\u0c95\u0c9f\u0ccd\u0c9f\u0cc6"
    "Welcome" -> "\u0cb8\u0ccd\u0cb5\u0cbe\u0c97\u0ca4"
    "Skip" -> "\u0cac\u0cbf\u0c9f\u0ccd\u0c9f\u0cc1\u0cac\u0cbf\u0ca1\u0cbf"
    "Choose Your Role" -> "\u0ca8\u0cbf\u0cae\u0ccd\u0cae \u0caa\u0cbe\u0ca4\u0ccd\u0cb0\u0cb5\u0ca8\u0ccd\u0ca8\u0cc1 \u0c86\u0caf\u0ccd\u0c95\u0cc6\u0cae\u0cbe\u0ca1\u0cbf"
    "I'm a Worker" -> "\u0ca8\u0cbe\u0ca8\u0cc1 \u0c95\u0cc6\u0cb2\u0cb8\u0c97\u0cbe\u0cb0"
    "I'm a Customer" -> "\u0ca8\u0cbe\u0ca8\u0cc1 \u0c97\u0ccd\u0cb0\u0cbe\u0cb9\u0c95"
    "Showcase your skills, service cards and portfolio" -> "\u0ca8\u0cbf\u0cae\u0ccd\u0cae \u0c95\u0ccc\u0cb6\u0cb2\u0ccd\u0caf, \u0cb8\u0cc7\u0cb5\u0cc6\u0c97\u0cb3\u0cc1 \u0cae\u0ca4\u0ccd\u0ca4\u0cc1 \u0caa\u0ccb\u0cb0\u0ccd\u0c9f\u0ccd\u0cab\u0ccb\u0cb2\u0cbf\u0caf\u0ccb \u0ca4\u0ccb\u0cb0\u0cbf\u0cb8\u0cbf"
    "Find trusted skilled workers nearby" -> "\u0ca8\u0cbf\u0cae\u0ccd\u0cae \u0cb8\u0cae\u0cc0\u0caa\u0ca6 \u0ca8\u0c82\u0cac\u0cbf\u0c95\u0cc6\u0caf \u0c95\u0ccc\u0cb6\u0cb2\u0ccd\u0caf\u0cb5\u0c82\u0ca4\u0cb0\u0ca8\u0ccd\u0ca8\u0cc1 \u0cb9\u0cc1\u0ca1\u0cc1\u0c95\u0cbf"
    "Create Worker Profile" -> "\u0c95\u0cc6\u0cb2\u0cb8\u0c97\u0cbe\u0cb0\u0ca8 \u0caa\u0ccd\u0cb0\u0cca\u0cab\u0cc8\u0cb2\u0ccd \u0cb0\u0c9a\u0cbf\u0cb8\u0cbf"
    "Add photo from gallery" -> "\u0c97\u0ccd\u0caf\u0cbe\u0cb2\u0cb0\u0cbf\u0caf\u0cbf\u0c82\u0ca6 \u0cab\u0ccb\u0c9f\u0ccb \u0cb8\u0cc7\u0cb0\u0cbf\u0cb8\u0cbf"
    "Full Name *" -> "\u0caa\u0cc2\u0cb0\u0ccd\u0ca3 \u0cb9\u0cc6\u0cb8\u0cb0\u0cc1 *"
    "Phone Number *" -> "\u0cab\u0ccb\u0ca8\u0ccd \u0cb8\u0c82\u0c96\u0ccd\u0caf\u0cc6 *"
    "Email" -> "\u0c87\u0cae\u0cc7\u0cb2\u0ccd"
    "Location / Area *" -> "\u0cb8\u0ccd\u0ca5\u0cb3 / \u0caa\u0ccd\u0cb0\u0ca6\u0cc7\u0cb6 *"
    "About Me" -> "\u0ca8\u0ca8\u0ccd\u0ca8 \u0cac\u0c97\u0ccd\u0c97\u0cc6"
    "Create Profile" -> "\u0caa\u0ccd\u0cb0\u0cca\u0cab\u0cc8\u0cb2\u0ccd \u0cb0\u0c9a\u0cbf\u0cb8\u0cbf"
    "Save Profile" -> "\u0caa\u0ccd\u0cb0\u0cca\u0cab\u0cc8\u0cb2\u0ccd \u0c89\u0cb3\u0cbf\u0cb8\u0cbf"
    "Cancel" -> "\u0cb0\u0ca6\u0ccd\u0ca6\u0cc1"
    "Worker" -> "\u0c95\u0cc6\u0cb2\u0cb8\u0c97\u0cbe\u0cb0"
    "Welcome back, %s!" -> "%s, \u0cae\u0ca4\u0ccd\u0ca4\u0cc6 \u0cb8\u0ccd\u0cb5\u0cbe\u0c97\u0ca4!"
    "Home" -> "\u0cae\u0cc1\u0c96\u0caa\u0cc1\u0c9f"
    "Services" -> "\u0cb8\u0cc7\u0cb5\u0cc6\u0c97\u0cb3\u0cc1"
    "Portfolio" -> "\u0caa\u0ccb\u0cb0\u0ccd\u0c9f\u0ccd\u0cab\u0ccb\u0cb2\u0cbf\u0caf\u0ccb"
    "Requests" -> "\u0cb5\u0cbf\u0ca8\u0c82\u0ca4\u0cbf\u0c97\u0cb3\u0cc1"
    "Profile" -> "\u0caa\u0ccd\u0cb0\u0cca\u0cab\u0cc8\u0cb2\u0ccd"
    "Rating" -> "\u0cb0\u0cc7\u0c9f\u0cbf\u0c82\u0c97\u0ccd"
    "Reviews" -> "\u0cb5\u0cbf\u0cae\u0cb0\u0ccd\u0cb6\u0cc6\u0c97\u0cb3\u0cc1"
    "Pending" -> "\u0cac\u0cbe\u0c95\u0cbf"
    "Quick Actions" -> "\u0ca4\u0ccd\u0cb5\u0cb0\u0cbf\u0ca4 \u0c95\u0ccd\u0cb0\u0cbf\u0caf\u0cc6\u0c97\u0cb3\u0cc1"
    "Add Service" -> "\u0cb8\u0cc7\u0cb5\u0cc6 \u0cb8\u0cc7\u0cb0\u0cbf\u0cb8\u0cbf"
    "View Portfolio" -> "\u0caa\u0ccb\u0cb0\u0ccd\u0c9f\u0ccd\u0cab\u0ccb\u0cb2\u0cbf\u0caf\u0ccb \u0ca8\u0ccb\u0ca1\u0cbf"
    "Manage Profile" -> "\u0caa\u0ccd\u0cb0\u0cca\u0cab\u0cc8\u0cb2\u0ccd \u0ca8\u0cbf\u0cb0\u0ccd\u0cb5\u0cb9\u0cbf\u0cb8\u0cbf"
    "Add" -> "\u0cb8\u0cc7\u0cb0\u0cbf\u0cb8\u0cbf"
    "Search services" -> "\u0cb8\u0cc7\u0cb5\u0cc6\u0c97\u0cb3\u0ca8\u0ccd\u0ca8\u0cc1 \u0cb9\u0cc1\u0ca1\u0cc1\u0c95\u0cbf"
    "All" -> "\u0c8e\u0cb2\u0ccd\u0cb2\u0cbe"
    "Add New Service" -> "\u0cb9\u0cca\u0cb8 \u0cb8\u0cc7\u0cb5\u0cc6 \u0cb8\u0cc7\u0cb0\u0cbf\u0cb8\u0cbf"
    "Edit Service" -> "\u0cb8\u0cc7\u0cb5\u0cc6 \u0ca4\u0cbf\u0ca6\u0ccd\u0ca6\u0cbf"
    "Service Name *" -> "\u0cb8\u0cc7\u0cb5\u0cc6\u0caf \u0cb9\u0cc6\u0cb8\u0cb0\u0cc1 *"
    "Description" -> "\u0cb5\u0cbf\u0cb5\u0cb0\u0ca3\u0cc6"
    "Price Type *" -> "\u0cac\u0cc6\u0cb2\u0cc6 \u0ca4\u0cb0\u0cb9 *"
    "Fixed Price" -> "\u0ca8\u0cbf\u0c97\u0ca6\u0cbf\u0ca4 \u0cac\u0cc6\u0cb2\u0cc6"
    "Starting At" -> "\u0c86\u0cb0\u0c82\u0cad \u0ca6\u0cb0"
    "Price *" -> "\u0cac\u0cc6\u0cb2\u0cc6 *"
    "Rs" -> "\u20b9"
    "Save Service" -> "\u0cb8\u0cc7\u0cb5\u0cc6 \u0c89\u0cb3\u0cbf\u0cb8\u0cbf"
    "Delete Service" -> "\u0cb8\u0cc7\u0cb5\u0cc6 \u0c85\u0cb3\u0cbf\u0cb8\u0cbf"
    "My Portfolio" -> "\u0ca8\u0ca8\u0ccd\u0ca8 \u0caa\u0ccb\u0cb0\u0ccd\u0c9f\u0ccd\u0cab\u0ccb\u0cb2\u0cbf\u0caf\u0ccb"
    "Add Work" -> "\u0c95\u0cc6\u0cb2\u0cb8 \u0cb8\u0cc7\u0cb0\u0cbf\u0cb8\u0cbf"
    "Choose Photo From Gallery" -> "\u0c97\u0ccd\u0caf\u0cbe\u0cb2\u0cb0\u0cbf\u0caf\u0cbf\u0c82\u0ca6 \u0cab\u0ccb\u0c9f\u0ccb \u0c86\u0caf\u0ccd\u0c95\u0cc6\u0cae\u0cbe\u0ca1\u0cbf"
    "Upload" -> "\u0c85\u0caa\u0ccd\u0cb2\u0ccb\u0ca1\u0ccd"
    "Hire Requests" -> "\u0ca8\u0cc7\u0cae\u0c95\u0cbe\u0ca4\u0cbf \u0cb5\u0cbf\u0ca8\u0c82\u0ca4\u0cbf\u0c97\u0cb3\u0cc1"
    "Call" -> "\u0c95\u0cb0\u0cc6"
    "Hire Me" -> "\u0ca8\u0ca8\u0ccd\u0ca8\u0ca8\u0ccd\u0ca8\u0cc1 \u0ca8\u0cc7\u0cae\u0cbf\u0cb8\u0cbf"
    "Review" -> "\u0cb5\u0cbf\u0cae\u0cb0\u0ccd\u0cb6\u0cc6"
    "Settings" -> "\u0cb8\u0cc6\u0c9f\u0ccd\u0c9f\u0cbf\u0c82\u0c97\u0ccd\u0cb8\u0ccd"
    "Language: English" -> "\u0cad\u0cbe\u0cb7\u0cc6: \u0c95\u0ca8\u0ccd\u0ca8\u0ca1"
    "Language" -> "\u0cad\u0cbe\u0cb7\u0cc6"
    "English" -> "English"
    "Kannada" -> "\u0c95\u0ca8\u0ccd\u0ca8\u0ca1"
    "Logout" -> "\u0cb2\u0cbe\u0c97\u0ccd \u0c94\u0c9f\u0ccd"
    "Edit" -> "\u0ca4\u0cbf\u0ca6\u0ccd\u0ca6\u0cbf"
    "Delete" -> "\u0c85\u0cb3\u0cbf\u0cb8\u0cbf"
    "Find skilled workers in your area" -> "\u0ca8\u0cbf\u0cae\u0ccd\u0cae \u0caa\u0ccd\u0cb0\u0ca6\u0cc7\u0cb6\u0ca6\u0cb2\u0ccd\u0cb2\u0cbf \u0c95\u0ccc\u0cb6\u0cb2\u0ccd\u0caf\u0cb5\u0c82\u0ca4\u0cb0\u0ca8\u0ccd\u0ca8\u0cc1 \u0cb9\u0cc1\u0ca1\u0cc1\u0c95\u0cbf"
    "Search workers" -> "\u0c95\u0cc6\u0cb2\u0cb8\u0c97\u0cbe\u0cb0\u0cb0\u0ca8\u0ccd\u0ca8\u0cc1 \u0cb9\u0cc1\u0ca1\u0cc1\u0c95\u0cbf"
    else -> null
}

enum class AppScreen { Splash, Onboarding, WorkerRegister, WorkerDashboard, WorkerServices, ServiceForm, WorkerPortfolio, PortfolioForm, Requests, WorkerProfile, EditProfile, CustomerHome, WorkerDetail, Settings }

@Composable
fun KaushalyaApp(vm: KaushalyaViewModel) {
    val firstLaunch by vm.firstLaunch.collectAsState()
    val isWorker by vm.isWorker.collectAsState()
    val lang by vm.language.collectAsState()
    var screen by rememberSaveable { mutableStateOf(AppScreen.Splash) }
    var selectedWorkerId by rememberSaveable { mutableStateOf<Long?>(null) }
    var editServiceId by rememberSaveable { mutableStateOf<Long?>(null) }

    LaunchedEffect(Unit) {
        delay(900)
        screen = if (firstLaunch) AppScreen.Onboarding else if (isWorker) AppScreen.WorkerDashboard else AppScreen.CustomerHome
    }

    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        CompositionLocalProvider(
            LocalLang provides lang,
            LocalToggleLanguage provides { vm.setLanguage(if (lang == "en") "kn" else "en") }
        ) {
        when (screen) {
            AppScreen.Splash -> SplashScreen()
            AppScreen.Onboarding -> OnboardingScreen(
                onWorker = { screen = AppScreen.WorkerRegister },
                onCustomer = { vm.finishOnboarding(false); screen = AppScreen.CustomerHome }
            )
            AppScreen.WorkerRegister -> WorkerRegistrationScreen(vm, onDone = { screen = AppScreen.WorkerDashboard }, onCancel = { screen = AppScreen.Onboarding })
            AppScreen.WorkerDashboard -> WorkerDashboardScreen(vm, navigate = { screen = it })
            AppScreen.WorkerServices -> WorkerServicesScreen(vm, onAdd = { editServiceId = null; screen = AppScreen.ServiceForm }, onEdit = { editServiceId = it.serviceId; screen = AppScreen.ServiceForm }, onBack = { screen = AppScreen.WorkerDashboard })
            AppScreen.ServiceForm -> ServiceFormScreen(vm, editServiceId, onDone = { screen = AppScreen.WorkerServices })
            AppScreen.WorkerPortfolio -> WorkerPortfolioScreen(vm, onAdd = { screen = AppScreen.PortfolioForm }, onBack = { screen = AppScreen.WorkerDashboard })
            AppScreen.PortfolioForm -> PortfolioFormScreen(vm, onDone = { screen = AppScreen.WorkerPortfolio })
            AppScreen.Requests -> HireRequestsScreen(vm, onBack = { screen = AppScreen.WorkerDashboard })
            AppScreen.WorkerProfile -> WorkerProfileScreen(vm, onEdit = { screen = AppScreen.EditProfile }, onPublic = {
                selectedWorkerId = vm.currentWorker.value?.workerId
                screen = AppScreen.WorkerDetail
            }, onSettings = { screen = AppScreen.Settings }, onBack = { screen = AppScreen.WorkerDashboard })
            AppScreen.EditProfile -> EditProfileScreen(vm, onDone = { screen = AppScreen.WorkerProfile })
            AppScreen.CustomerHome -> CustomerHomeScreen(vm, onWorker = { selectedWorkerId = it; screen = AppScreen.WorkerDetail }, onOnboard = { screen = AppScreen.Onboarding })
            AppScreen.WorkerDetail -> WorkerDetailScreen(vm, selectedWorkerId ?: 0L, onBack = { screen = if (isWorker) AppScreen.WorkerProfile else AppScreen.CustomerHome })
            AppScreen.Settings -> SettingsScreen(vm, onBack = { screen = AppScreen.WorkerProfile }, onLogout = { vm.logout(); screen = AppScreen.Onboarding })
        }
        }
    }
}

@Composable
fun SplashScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Icon(Icons.Default.Build, contentDescription = null, modifier = Modifier.size(86.dp), tint = MaterialTheme.colorScheme.primary)
            Text(t("Kaushalya-Karnataka"), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text(t("Your Local Skills Marketplace"), color = MaterialTheme.colorScheme.onSurfaceVariant)
            CircularProgressIndicator(Modifier.padding(top = 16.dp))
        }
    }
}

@Composable
fun OnboardingScreen(onWorker: () -> Unit, onCustomer: () -> Unit) {
    Scaffold(topBar = { TopBar(t("Welcome"), action = { TextButton(onClick = onCustomer) { Text(t("Skip")) } }) }) { pad ->
        Column(Modifier.padding(pad).padding(20.dp), verticalArrangement = Arrangement.spacedBy(18.dp)) {
            Text(t("Choose Your Role"), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            RoleCard(t("I'm a Worker"), t("Showcase your skills, service cards and portfolio"), Icons.Default.Build, onWorker)
            RoleCard(t("I'm a Customer"), t("Find trusted skilled workers nearby"), Icons.Default.Home, onCustomer)
        }
    }
}

@Composable
fun RoleCard(title: String, sub: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Card(onClick = onClick, shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp)) {
        Row(Modifier.fillMaxWidth().padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, Modifier.size(48.dp), tint = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.width(16.dp))
            Column {
                Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text(sub, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String, onBack: (() -> Unit)? = null, action: @Composable (() -> Unit)? = null) {
    val lang = LocalLang.current
    val toggleLanguage = LocalToggleLanguage.current
    CenterAlignedTopAppBar(
        title = { Text(title, maxLines = 1, overflow = TextOverflow.Ellipsis) },
        navigationIcon = { if (onBack != null) IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) } },
        actions = {
            IconButton(onClick = toggleLanguage) {
                Icon(
                    Icons.Default.Language,
                    contentDescription = if (lang == "en") "Switch to Kannada" else "Switch to English",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            action?.invoke()
        }
    )
}

@Composable
fun WorkerRegistrationScreen(vm: KaushalyaViewModel, onDone: () -> Unit, onCancel: () -> Unit) {
    val categories by vm.categories.collectAsState()
    WorkerForm(
        title = "Create Worker Profile",
        categories = categories.map { it.categoryName },
        initial = null,
        onSave = {
            vm.registerWorker(it)
            onDone()
        },
        onCancel = onCancel
    )
}

@Composable
fun EditProfileScreen(vm: KaushalyaViewModel, onDone: () -> Unit) {
    val worker by vm.currentWorker.collectAsState()
    val categories by vm.categories.collectAsState()
    worker?.let {
        WorkerForm("Edit Profile", categories.map { c -> c.categoryName }, it, onSave = { updated ->
            vm.updateWorker(updated.copy(workerId = it.workerId, createdAt = it.createdAt))
            onDone()
        }, onCancel = onDone)
    }
}

@Composable
fun WorkerForm(title: String, categories: List<String>, initial: Worker?, onSave: (Worker) -> Unit, onCancel: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var image by rememberSaveable { mutableStateOf(initial?.profileImage) }
    var name by rememberSaveable { mutableStateOf(initial?.name.orEmpty()) }
    var phone by rememberSaveable { mutableStateOf(initial?.phone.orEmpty()) }
    var email by rememberSaveable { mutableStateOf(initial?.email.orEmpty()) }
    var location by rememberSaveable { mutableStateOf(initial?.location.orEmpty()) }
    var category by rememberSaveable { mutableStateOf(initial?.primaryCategory ?: categories.firstOrNull().orEmpty()) }
    var bio by rememberSaveable { mutableStateOf(initial?.bio.orEmpty()) }
    var error by remember { mutableStateOf<String?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) scope.launch { image = saveCompressedImage(context, uri) }
    }

    Scaffold(topBar = { TopBar(title, onCancel) }) { pad ->
        LazyColumn(Modifier.padding(pad), contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                ProfileImage(image, Modifier.size(112.dp).clickable { launcher.launch("image/*") })
                TextButton(onClick = { launcher.launch("image/*") }) { Icon(Icons.Default.CameraAlt, null); Spacer(Modifier.width(6.dp)); Text(t("Add photo from gallery")) }
            }
            item { OutlinedTextField(name, { name = it }, label = { Text(t("Full Name *")) }, modifier = Modifier.fillMaxWidth()) }
            item { OutlinedTextField(phone, { phone = it.filter(Char::isDigit).take(10) }, label = { Text(t("Phone Number *")) }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), modifier = Modifier.fillMaxWidth()) }
            item { OutlinedTextField(email, { email = it }, label = { Text(t("Email")) }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), modifier = Modifier.fillMaxWidth()) }
            item { OutlinedTextField(location, { location = it }, label = { Text(t("Location / Area *")) }, modifier = Modifier.fillMaxWidth()) }
            item { CategoryChips(categories, category) { category = it } }
            item { OutlinedTextField(bio, { bio = it }, label = { Text(t("About Me")) }, minLines = 3, modifier = Modifier.fillMaxWidth()) }
            item { error?.let { Text(t(it), color = MaterialTheme.colorScheme.error) } }
            item {
                 Button(onClick = {
                     error = validateWorker(name, phone, email, category)
                     if (error == null) onSave(Worker(name = name.trim(), phone = phone, email = email.ifBlank { null }, profileImage = image, bio = bio.ifBlank { null }, location = location.trim(), primaryCategory = category))
                 }, modifier = Modifier.fillMaxWidth()) { Text(if (initial == null) t("Create Profile") else t("Save Profile")) }
                 OutlinedButton(onClick = onCancel, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) { Text(t("Cancel")) }
            }
        }
    }
}

fun validateWorker(name: String, phone: String, email: String, category: String): String? = when {
    name.trim().length < 3 -> "Name must be at least 3 characters."
    phone.length != 10 -> "Phone number must be 10 digits."
    email.isNotBlank() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Enter a valid email address."
    category.isBlank() -> "Select a primary category."
    else -> null
}

@Composable
fun WorkerDashboardScreen(vm: KaushalyaViewModel, navigate: (AppScreen) -> Unit) {
    val worker by vm.currentWorker.collectAsState()
    val services by vm.services.collectAsState()
    val reviews by vm.reviews.collectAsState()
    val requests by vm.requests.collectAsState()
    WorkerScaffold(vm, selected = AppScreen.WorkerDashboard, navigate = navigate) { pad ->
        LazyColumn(Modifier.padding(pad), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            item { Text(t("Welcome back, %s!", worker?.name ?: t("Worker")), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold) }
            item {
                val avg = reviews.map { it.rating }.average().takeIf { !it.isNaN() } ?: 0.0
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        StatCard("Services", services.size.toString(), Modifier.weight(1f))
                        StatCard("Rating", "%.1f".format(avg), Modifier.weight(1f))
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        StatCard("Reviews", reviews.size.toString(), Modifier.weight(1f))
                        StatCard("Pending", requests.count { it.status == "PENDING" }.toString(), Modifier.weight(1f))
                    }
                }
            }
            item { Text(t("Quick Actions"), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold) }
            item { ActionButton(t("Add Service"), Icons.Default.Add) { navigate(AppScreen.ServiceForm) } }
            item { ActionButton(t("View Portfolio"), Icons.Default.Image) { navigate(AppScreen.WorkerPortfolio) } }
            item { ActionButton(t("Manage Profile"), Icons.Default.Person) { navigate(AppScreen.WorkerProfile) } }
        }
    }
}

@Composable
fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier, shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(Color.White)) {
        Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun ActionButton(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp)) {
        Icon(icon, null)
        Spacer(Modifier.width(8.dp))
        Text(text)
    }
}

@Composable
fun WorkerScaffold(vm: KaushalyaViewModel, selected: AppScreen, navigate: (AppScreen) -> Unit, content: @Composable (PaddingValues) -> Unit) {
    val requests by vm.requests.collectAsState()
    Scaffold(
        topBar = {
            TopBar("Kaushalya-Karnataka", action = {
                IconButton(onClick = { navigate(AppScreen.Requests) }) {
                    BadgedIcon(Icons.Default.Notifications, requests.count { it.status == "PENDING" }.toString())
                }
            })
        },
        bottomBar = {
            NavigationBar {
                listOf(
                    AppScreen.WorkerDashboard to (Icons.Default.Home to t("Home")),
                    AppScreen.WorkerServices to (Icons.Default.Build to t("Services")),
                    AppScreen.WorkerPortfolio to (Icons.Default.Image to t("Portfolio")),
                    AppScreen.Requests to (Icons.Default.Notifications to t("Requests")),
                    AppScreen.WorkerProfile to (Icons.Default.Person to t("Profile"))
                ).forEach { (screen, pair) ->
                    NavigationBarItem(selected = selected == screen, onClick = { navigate(screen) }, icon = { Icon(pair.first, null) }, label = { Text(pair.second) })
                }
            }
        },
        content = content
    )
}

@Composable
fun BadgedIcon(icon: androidx.compose.ui.graphics.vector.ImageVector, badge: String) {
    Box {
        Icon(icon, null)
        if (badge != "0") Text(badge, Modifier.align(Alignment.TopEnd).background(MaterialTheme.colorScheme.secondary, CircleShape).padding(horizontal = 4.dp), style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
fun WorkerServicesScreen(vm: KaushalyaViewModel, onAdd: () -> Unit, onEdit: (WorkerService) -> Unit, onBack: () -> Unit) {
    val services by vm.services.collectAsState()
    val categories by vm.categories.collectAsState()
    var query by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf<String?>(null) }
    val filtered = services.filter { (query.isBlank() || it.serviceName.contains(query, true)) && (category == null || it.category == category) }
    val allLabel = t("All")
    Scaffold(topBar = { TopBar(t("Services"), onBack) }, floatingActionButton = { ExtendedFloatingActionButton(onClick = onAdd, icon = { Icon(Icons.Default.Add, null) }, text = { Text(t("Add")) }) }) { pad ->
        LazyColumn(Modifier.padding(pad), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            item { OutlinedTextField(query, { query = it }, leadingIcon = { Icon(Icons.Default.Search, null) }, label = { Text(t("Search services")) }, modifier = Modifier.fillMaxWidth()) }
            item { CategoryFilterRow(listOf(allLabel) + categories.map { it.categoryName }, category ?: allLabel) { category = if (it == allLabel) null else it } }
            items(filtered, key = { it.serviceId }) { service ->
                ServiceCard(service, onEdit = { onEdit(service) }, onDelete = { vm.deleteService(service) }, onToggle = { vm.saveService(service.copy(isActive = it)) })
            }
        }
    }
}

@Composable
fun ServiceCard(service: WorkerService, onEdit: () -> Unit, onDelete: () -> Unit, onToggle: (Boolean) -> Unit) {
    Card(shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(Color.White), elevation = CardDefaults.cardElevation(1.dp)) {
        Column(Modifier.fillMaxWidth().padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Build, null, tint = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.width(10.dp))
                Column(Modifier.weight(1f)) {
                    Text(service.serviceName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(service.category, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Switch(service.isActive, onCheckedChange = onToggle)
            }
            service.description?.let { Text(it, color = MaterialTheme.colorScheme.onSurfaceVariant) }
            Text(t("%s: Rs %d", if (service.priceType == "FIXED") t("Fixed Price") else t("Starting At"), service.price.roundToInt()), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = onEdit) { Icon(Icons.Default.Edit, null); Text(t("Edit")) }
                TextButton(onClick = onDelete) { Icon(Icons.Default.Delete, null); Text(t("Delete")) }
            }
        }
    }
}

@Composable
fun ServiceFormScreen(vm: KaushalyaViewModel, editServiceId: Long?, onDone: () -> Unit) {
    val services by vm.services.collectAsState()
    val worker by vm.currentWorker.collectAsState()
    val categories by vm.categories.collectAsState()
    val existing = services.firstOrNull { it.serviceId == editServiceId }
    var category by rememberSaveable(existing?.serviceId) { mutableStateOf(existing?.category ?: categories.firstOrNull()?.categoryName.orEmpty()) }
    var name by rememberSaveable(existing?.serviceId) { mutableStateOf(existing?.serviceName.orEmpty()) }
    var description by rememberSaveable(existing?.serviceId) { mutableStateOf(existing?.description.orEmpty()) }
    var priceType by rememberSaveable(existing?.serviceId) { mutableStateOf(existing?.priceType ?: "FIXED") }
    var price by rememberSaveable(existing?.serviceId) { mutableStateOf(existing?.price?.roundToInt()?.toString().orEmpty()) }
    var error by remember { mutableStateOf<String?>(null) }
    Scaffold(topBar = { TopBar(if (existing == null) t("Add New Service") else t("Edit Service"), onDone) }) { pad ->
        LazyColumn(Modifier.padding(pad), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item { CategoryChips(categories.map { it.categoryName }, category) { category = it } }
            item { OutlinedTextField(name, { name = it }, label = { Text(t("Service Name *")) }, modifier = Modifier.fillMaxWidth()) }
            item { OutlinedTextField(description, { description = it }, label = { Text(t("Description")) }, minLines = 3, modifier = Modifier.fillMaxWidth()) }
            item {
                Text(t("Price Type *"), fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(priceType == "FIXED", { priceType = "FIXED" }); Text(t("Fixed Price"))
                    RadioButton(priceType == "STARTING_AT", { priceType = "STARTING_AT" }); Text(t("Starting At"))
                }
            }
            item { OutlinedTextField(price, { price = it.filter { ch -> ch.isDigit() } }, label = { Text(t("Price *")) }, prefix = { Text(t("Rs")) }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth()) }
            item { error?.let { Text(t(it), color = MaterialTheme.colorScheme.error) } }
            item {
                Button(onClick = {
                    val amount = price.toDoubleOrNull()
                    error = when {
                        category.isBlank() -> "Select a category."
                        name.trim().length < 3 -> "Service name must be at least 3 characters."
                        amount == null || amount <= 0.0 -> "Enter a positive price."
                        else -> null
                    }
                    if (error == null && worker != null) {
                        vm.saveService(WorkerService(existing?.serviceId ?: 0, worker!!.workerId, category, name.trim(), description.ifBlank { null }, priceType, amount!!, existing?.createdAt ?: System.currentTimeMillis(), existing?.isActive ?: true))
                        onDone()
                    }
                }, modifier = Modifier.fillMaxWidth()) { Text(t("Save Service")) }
                if (existing != null) OutlinedButton(onClick = { vm.deleteService(existing); onDone() }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) { Icon(Icons.Default.Delete, null); Text(t("Delete Service")) }
            }
        }
    }
}

@Composable
fun WorkerPortfolioScreen(vm: KaushalyaViewModel, onAdd: () -> Unit, onBack: () -> Unit) {
    val items by vm.portfolio.collectAsState()
    Scaffold(topBar = { TopBar(t("My Portfolio"), onBack) }, floatingActionButton = { ExtendedFloatingActionButton(onClick = onAdd, icon = { Icon(Icons.Default.Add, null) }, text = { Text(t("Add Work")) }) }) { pad ->
        Column(Modifier.padding(pad).padding(16.dp)) {
            Text(t("Upload your work photos to build community trust."), color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(12.dp))
            LazyVerticalGrid(columns = GridCells.Fixed(2), verticalArrangement = Arrangement.spacedBy(10.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(items, key = { it.portfolioId }) { item -> PortfolioTile(item) { vm.deletePortfolio(item) } }
            }
        }
    }
}

@Composable
fun PortfolioTile(item: PortfolioItem, onDelete: () -> Unit) {
    Card(shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(Color.White)) {
        Box {
            AsyncImage(model = File(item.imageUri), contentDescription = item.title, modifier = Modifier.fillMaxWidth().aspectRatio(1f), contentScale = ContentScale.Crop)
            IconButton(onClick = onDelete, modifier = Modifier.align(Alignment.TopEnd).background(Color.White.copy(alpha = 0.8f), CircleShape)) { Icon(Icons.Default.Delete, null) }
            Text(item.title ?: "Work photo", Modifier.align(Alignment.BottomStart).fillMaxWidth().background(Color.Black.copy(alpha = 0.55f)).padding(8.dp), color = Color.White, maxLines = 2)
        }
    }
}

@Composable
fun PortfolioFormScreen(vm: KaushalyaViewModel, onDone: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val worker by vm.currentWorker.collectAsState()
    val categories by vm.categories.collectAsState()
    var picked by rememberSaveable { mutableStateOf<String?>(null) }
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf(categories.firstOrNull()?.categoryName.orEmpty()) }
    var error by remember { mutableStateOf<String?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) scope.launch { picked = saveCompressedImage(context, uri) }
    }
    Scaffold(topBar = { TopBar(t("Add Portfolio Item"), onDone) }) { pad ->
        LazyColumn(Modifier.padding(pad), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                Box(Modifier.fillMaxWidth().aspectRatio(1.4f).clip(RoundedCornerShape(8.dp)).background(Color(0xFFE9EEF5)), contentAlignment = Alignment.Center) {
                    if (picked != null) AsyncImage(File(picked!!), null, Modifier.fillMaxSize(), contentScale = ContentScale.Crop) else Text(t("No image selected"))
                }
            }
            item { Button(onClick = { launcher.launch("image/*") }, modifier = Modifier.fillMaxWidth()) { Icon(Icons.Default.Image, null); Spacer(Modifier.width(8.dp)); Text(t("Choose Photo From Gallery")) } }
            item { OutlinedTextField(title, { title = it }, label = { Text(t("Work Title")) }, modifier = Modifier.fillMaxWidth()) }
            item { OutlinedTextField(description, { description = it }, label = { Text(t("Description")) }, minLines = 3, modifier = Modifier.fillMaxWidth()) }
            item { CategoryChips(categories.map { it.categoryName }, category) { category = it } }
            item { error?.let { Text(t(it), color = MaterialTheme.colorScheme.error) } }
            item {
                Button(onClick = {
                        if (picked == null) error = "Select an image first." else {
                        worker?.let { vm.addPortfolio(PortfolioItem(workerId = it.workerId, imageUri = picked!!, title = title.ifBlank { null }, description = description.ifBlank { null }, serviceCategory = category.ifBlank { null })) }
                        onDone()
                    }
                }, modifier = Modifier.fillMaxWidth()) { Text(t("Upload")) }
            }
        }
    }
}

@Composable
fun HireRequestsScreen(vm: KaushalyaViewModel, onBack: () -> Unit) {
    val requests by vm.requests.collectAsState()
    var tab by rememberSaveable { mutableStateOf("PENDING") }
    val tabs = listOf("PENDING", "ACCEPTED", "COMPLETED", "ALL")
    Scaffold(topBar = { TopBar(t("Hire Requests"), onBack) }) { pad ->
        Column(Modifier.padding(pad)) {
            TabRow(tabs.indexOf(tab)) { tabs.forEach { Tab(selected = tab == it, onClick = { tab = it }, text = { Text(t(it)) }) } }
            LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(requests.filter { tab == "ALL" || it.status == tab }, key = { it.requestId }) { request ->
                    RequestCard(request, onStatus = { vm.updateRequest(request.copy(status = it)) }, onDelete = { vm.deleteRequest(request) })
                }
            }
        }
    }
}

@Composable
fun RequestCard(request: HireRequest, onStatus: (String) -> Unit, onDelete: () -> Unit) {
    val context = LocalContext.current
    Card(shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(Color.White)) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(request.customerName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(t("Phone: %s", request.customerPhone))
            Text(t("Service: %s", request.serviceRequested))
            request.message?.let { Text("\"" + it + "\"", color = MaterialTheme.colorScheme.onSurfaceVariant) }
            Text(timeLabel(request.createdAt) + " • " + t(request.status), color = MaterialTheme.colorScheme.primary)
            Row {
                if (request.status == "PENDING") {
                    TextButton(onClick = { onStatus("ACCEPTED") }) { Icon(Icons.Default.Check, null); Text(t("Accept")) }
                    TextButton(onClick = { onStatus("REJECTED") }) { Text(t("Reject")) }
                }
                TextButton(onClick = { onStatus("COMPLETED") }) { Text(t("Complete")) }
                TextButton(onClick = { openDialer(context, request.customerPhone) }) { Icon(Icons.Default.Call, null); Text(t("Call")) }
                TextButton(onClick = onDelete) { Icon(Icons.Default.Delete, null); Text(t("Delete")) }
            }
        }
    }
}

@Composable
fun WorkerProfileScreen(vm: KaushalyaViewModel, onEdit: () -> Unit, onPublic: () -> Unit, onSettings: () -> Unit, onBack: () -> Unit) {
    val worker by vm.currentWorker.collectAsState()
    val services by vm.services.collectAsState()
    val reviews by vm.reviews.collectAsState()
    val avg = reviews.map { it.rating }.average().takeIf { !it.isNaN() } ?: 0.0
    Scaffold(topBar = { TopBar(t("My Profile"), onBack, action = { IconButton(onClick = onSettings) { Icon(Icons.Default.Settings, null) } }) }) { pad ->
        worker?.let {
            LazyColumn(Modifier.padding(pad), contentPadding = PaddingValues(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
                item { ProfileImage(it.profileImage, Modifier.size(120.dp)) }
                item { Text(it.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold) }
                item { RatingLine(avg, reviews.size) }
                item {
                    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(t("Phone: %s", it.phone))
                        Text(t("Email: %s", it.email ?: "-"))
                        Text(t("Location: %s", it.location))
                        Text(t("Primary: %s", it.primaryCategory))
                        Text(t("About: %s", it.bio ?: "-"))
                        Text(t("Member Since: %s", dateLabel(it.createdAt)))
                    }
                }
                item {
                    Card(shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(Color.White)) {
                        Column(Modifier.fillMaxWidth().padding(16.dp)) {
                            Text(t("%d Services", services.size))
                            Text(t("%.1f Avg Rating", "%.1f".format(avg)))
                            Text(t("%d Reviews", reviews.size))
                        }
                    }
                }
                item { Button(onEdit, Modifier.fillMaxWidth()) { Icon(Icons.Default.Edit, null); Text(t("Edit Profile")) } }
                item { OutlinedButton(onPublic, Modifier.fillMaxWidth()) { Text(t("View Public Profile")) } }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerHomeScreen(vm: KaushalyaViewModel, onWorker: (Long) -> Unit, onOnboard: () -> Unit) {
    val workers by vm.workersWithStats.collectAsState()
    val categories by vm.categories.collectAsState()
    val query by vm.search.collectAsState()
    val selected by vm.selectedCategory.collectAsState()
    var showFilters by remember { mutableStateOf(false) }
    val allLabel = t("All")
    Scaffold(topBar = {
        TopBar("Kaushalya-Karnataka", action = {
            IconButton(onClick = { showFilters = true }) { Icon(Icons.Default.FilterList, null) }
            IconButton(onClick = onOnboard) { Icon(Icons.Default.Person, null) }
        })
    }) { pad ->
        LazyColumn(Modifier.padding(pad), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item { Text(t("Find skilled workers in your area"), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold) }
            item { OutlinedTextField(query, { vm.search.value = it }, leadingIcon = { Icon(Icons.Default.Search, null) }, label = { Text(t("Search workers")) }, modifier = Modifier.fillMaxWidth()) }
            item { CategoryFilterRow(listOf(allLabel) + categories.map { it.categoryName }, selected ?: allLabel) { vm.selectedCategory.value = if (it == allLabel) null else it } }
            items(workers, key = { it.worker.workerId }) { WorkerCard(it) { onWorker(it.worker.workerId) } }
        }
        if (showFilters) FilterSheet(vm, categories.map { it.categoryName }, onDismiss = { showFilters = false })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSheet(vm: KaushalyaViewModel, categories: List<String>, onDismiss: () -> Unit) {
    val selected by vm.selectedCategory.collectAsState()
    val minRating by vm.minRating.collectAsState()
    val priceRange by vm.priceRange.collectAsState()
    val sortBy by vm.sortBy.collectAsState()
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Text(t("Filter Workers"), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            categories.forEach {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(selected == it, onCheckedChange = { checked -> vm.selectedCategory.value = if (checked) it else null })
                    Text(it)
                }
            }
            Text(t("Minimum Rating: %d", minRating.roundToInt()))
            Slider(value = minRating, onValueChange = { vm.minRating.value = it }, valueRange = 0f..5f, steps = 4)
            Text(t("Price Range: Rs %d - Rs %d", priceRange.start.roundToInt(), priceRange.endInclusive.roundToInt()))
            RangeSlider(value = priceRange, onValueChange = { vm.priceRange.value = it }, valueRange = 0f..10000f)
            Text(t("Sort By"), fontWeight = FontWeight.Bold)
            listOf("RATING" to t("Rating (High to Low)"), "REVIEWS" to t("Most Reviewed"), "PRICE" to t("Price (Low to High)")).forEach { (key, label) ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = sortBy == key, onClick = { vm.sortBy.value = key })
                    Text(label)
                }
            }
            Button(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) { Text(t("Apply Filters")) }
            OutlinedButton(onClick = {
                vm.selectedCategory.value = null
                vm.minRating.value = 0f
                vm.priceRange.value = 0f..10000f
                vm.sortBy.value = "RATING"
            }, modifier = Modifier.fillMaxWidth()) { Text(t("Reset")) }
        }
    }
}

@Composable
fun WorkerCard(data: WorkerWithStats, onClick: () -> Unit) {
    Card(onClick = onClick, shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(Color.White), elevation = CardDefaults.cardElevation(1.dp)) {
        Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            ProfileImage(data.worker.profileImage, Modifier.size(76.dp))
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(data.worker.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(data.worker.primaryCategory, color = MaterialTheme.colorScheme.onSurfaceVariant)
                RatingLine(data.avgRating, data.reviewCount)
                Text(t("From Rs %d", data.minPrice.roundToInt()), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            }
            Button(onClick = onClick) { Text(t("View")) }
        }
    }
}

@Composable
fun WorkerDetailScreen(vm: KaushalyaViewModel, workerId: Long, onBack: () -> Unit) {
    val context = LocalContext.current
    val workers by vm.workersWithStats.collectAsState()
    val data = workers.firstOrNull { it.worker.workerId == workerId }
    var tab by rememberSaveable { mutableIntStateOf(0) }
    var showHire by remember { mutableStateOf(false) }
    var showReview by remember { mutableStateOf(false) }
    if (data == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        return
    }
    val servicesFlow = remember(workerId) { KaushalyaDatabase.get(context).serviceDao().observeByWorker(workerId) }
    val portfolioFlow = remember(workerId) { KaushalyaDatabase.get(context).portfolioDao().observeByWorker(workerId) }
    val reviewsFlow = remember(workerId) { KaushalyaDatabase.get(context).reviewDao().observeByWorker(workerId) }
    val services by servicesFlow.collectAsState(initial = emptyList())
    val portfolio by portfolioFlow.collectAsState(initial = emptyList())
    val reviews by reviewsFlow.collectAsState(initial = emptyList())

    Scaffold(topBar = { TopBar("", onBack) }) { pad ->
        LazyColumn(Modifier.padding(pad), contentPadding = PaddingValues(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item { ProfileImage(data.worker.profileImage, Modifier.size(118.dp)) }
            item { Text(data.worker.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold) }
            item { AssistChip(onClick = {}, label = { Text(data.worker.primaryCategory) }, leadingIcon = { Icon(Icons.Default.Build, null) }) }
            item { RatingLine(data.avgRating, data.reviewCount) }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedButton(onClick = { openDialer(context, data.worker.phone) }) { Icon(Icons.Default.Call, null); Text(t("Call")) }
                    Button(onClick = { showHire = true }) { Text(t("Hire Me")) }
                    OutlinedButton(onClick = { showReview = true }) { Icon(Icons.Default.Star, null); Text(t("Review")) }
                }
            }
            item { TabRow(tab) { listOf(t("Services"), t("Portfolio"), t("Reviews")).forEachIndexed { index, label -> Tab(selected = tab == index, onClick = { tab = index }, text = { Text(label) }) } } }
            when (tab) {
                0 -> items(services.filter { it.isActive }, key = { it.serviceId }) { ServiceCard(it, onEdit = {}, onDelete = {}, onToggle = {}) }
                1 -> item {
                    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.height(420.dp), verticalArrangement = Arrangement.spacedBy(10.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(portfolio, key = { it.portfolioId }) { PortfolioTile(it, onDelete = {}) }
                    }
                }
                2 -> items(reviews, key = { it.reviewId }) { ReviewCard(it) }
            }
        }
    }
    if (showHire) HireDialog(data.worker, services.filter { it.isActive }, onDismiss = { showHire = false }) { request ->
        vm.addHireRequest(request)
        showHire = false
    }
    if (showReview) ReviewDialog(data.worker, onDismiss = { showReview = false }) { review ->
        vm.addReview(review)
        showReview = false
    }
}

@Composable
fun HireDialog(worker: Worker, services: List<WorkerService>, onDismiss: () -> Unit, onSend: (HireRequest) -> Unit) {
    var name by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var service by rememberSaveable { mutableStateOf(services.firstOrNull()?.serviceName.orEmpty()) }
    var message by rememberSaveable { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    AlertDialog(onDismissRequest = onDismiss, title = { Text(t("Request Service")) }, text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(t("To: %s", worker.name))
            OutlinedTextField(name, { name = it }, label = { Text(t("Your Name *")) })
            OutlinedTextField(phone, { phone = it.filter(Char::isDigit).take(10) }, label = { Text(t("Your Phone *")) }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone))
            CategoryChips(services.map { it.serviceName }, service) { service = it }
            OutlinedTextField(message, { message = it }, label = { Text(t("Message")) }, minLines = 2)
            error?.let { Text(t(it), color = MaterialTheme.colorScheme.error) }
        }
    }, confirmButton = {
        Button(onClick = {
            error = when {
                name.trim().length < 3 -> "Name must be at least 3 characters."
                phone.length != 10 -> "Phone must be 10 digits."
                service.isBlank() -> "Select a service."
                else -> null
            }
            if (error == null) onSend(HireRequest(workerId = worker.workerId, customerName = name.trim(), customerPhone = phone, serviceRequested = service, message = message.ifBlank { null }))
        }) { Text(t("Send")) }
    }, dismissButton = { TextButton(onClick = onDismiss) { Text(t("Cancel")) } })
}

@Composable
fun ReviewDialog(worker: Worker, onDismiss: () -> Unit, onSend: (Review) -> Unit) {
    var rating by rememberSaveable { mutableIntStateOf(5) }
    var name by rememberSaveable { mutableStateOf("") }
    var review by rememberSaveable { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    AlertDialog(onDismissRequest = onDismiss, title = { Text(t("Write a Review")) }, text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(t("For: %s", worker.name))
            Row { (1..5).forEach { Icon(Icons.Default.Star, null, Modifier.size(34.dp).clickable { rating = it }, tint = if (it <= rating) Color(0xFFFFC107) else Color.LightGray) } }
            OutlinedTextField(name, { name = it }, label = { Text(t("Your Name *")) })
            OutlinedTextField(review, { review = it }, label = { Text(t("Your Review *")) }, minLines = 3)
            error?.let { Text(t(it), color = MaterialTheme.colorScheme.error) }
        }
    }, confirmButton = {
        Button(onClick = {
            error = when {
                rating !in 1..5 -> "Select a rating."
                name.trim().length < 2 -> "Name must be at least 2 characters."
                review.trim().length < 10 -> "Review must be at least 10 characters."
                else -> null
            }
            if (error == null) onSend(Review(workerId = worker.workerId, customerName = name.trim(), rating = rating, reviewText = review.trim()))
        }) { Text(t("Submit")) }
    }, dismissButton = { TextButton(onClick = onDismiss) { Text(t("Cancel")) } })
}

@Composable
fun ReviewCard(review: Review) {
    Card(shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(Color.White)) {
        Column(Modifier.fillMaxWidth().padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(review.customerName, Modifier.weight(1f), fontWeight = FontWeight.Bold)
                Text("★".repeat(review.rating), color = Color(0xFFFFC107))
            }
            Text(review.reviewText)
            Text(timeLabel(review.createdAt), color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
fun SettingsScreen(vm: KaushalyaViewModel, onBack: () -> Unit, onLogout: () -> Unit) {
    val lang by vm.language.collectAsState()
    Scaffold(topBar = { TopBar(t("Settings"), onBack) }) { pad ->
        LazyColumn(Modifier.padding(pad), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(listOf("Account Settings", "Notifications", "Privacy", "About App", "Help & Support")) { key ->
                Card(shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(Color.White)) { Text(t(key), Modifier.fillMaxWidth().padding(18.dp), style = MaterialTheme.typography.titleMedium) }
            }
            item {
                Card(shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(Color.White)) {
                    Row(Modifier.fillMaxWidth().padding(18.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(t("Language: English"))
                        Row {
                            OutlinedButton(onClick = { vm.setLanguage("en") }, modifier = Modifier.padding(end = 8.dp)) { Text("English") }
                            OutlinedButton(onClick = { vm.setLanguage("kn") }) { Text("ಕನ್ನಡ") }
                        }
                    }
                }
            }
            item { Button(onClick = onLogout, modifier = Modifier.fillMaxWidth()) { Text(t("Logout")) } }
        }
    }
}

@Composable
fun ProfileImage(path: String?, modifier: Modifier = Modifier) {
    Box(modifier.clip(CircleShape).background(Color(0xFFE1ECF8)), contentAlignment = Alignment.Center) {
        if (!path.isNullOrBlank()) Image(rememberAsyncImagePainter(File(path)), null, Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
        else Icon(Icons.Default.Person, null, Modifier.size(42.dp), tint = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun RatingLine(avg: Double, count: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.Star, null, tint = Color(0xFFFFC107), modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(4.dp))
        Text("%.1f (%d reviews)".format(avg, count), color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryChips(categories: List<String>, selected: String, onSelect: (String) -> Unit) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        categories.filter { it.isNotBlank() }.forEach { chip ->
            FilterChip(selected = selected == chip, onClick = { onSelect(chip) }, label = { Text(chip) })
        }
    }
}

@Composable
fun CategoryFilterRow(categories: List<String>, selected: String, onSelect: (String) -> Unit) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(categories) { chip -> FilterChip(selected = selected == chip, onClick = { onSelect(chip) }, label = { Text(chip) }) }
    }
}

suspend fun saveCompressedImage(context: Context, uri: Uri): String = withContext(Dispatchers.IO) {
    val input = context.contentResolver.openInputStream(uri)
    val bitmap = BitmapFactory.decodeStream(input)
    input?.close()
    val dir = File(context.filesDir, "images").apply { mkdirs() }
    val file = File(dir, "IMG_${System.currentTimeMillis()}.jpg")
    FileOutputStream(file).use { out ->
        val width = bitmap.width.coerceAtMost(1280)
        val ratio = width.toFloat() / bitmap.width.toFloat()
        val scaled = if (ratio < 1f) Bitmap.createScaledBitmap(bitmap, width, (bitmap.height * ratio).roundToInt(), true) else bitmap
        scaled.compress(Bitmap.CompressFormat.JPEG, 78, out)
        if (scaled !== bitmap) scaled.recycle()
        bitmap.recycle()
    }
    file.absolutePath
}

@Composable
fun timeLabel(time: Long): String {
    val diff = System.currentTimeMillis() - time
    return when {
        diff < 60_000 -> t("Just now")
        diff < 3_600_000 -> t("%d min ago", (diff / 60_000).toInt())
        diff < 86_400_000 -> t("%d hours ago", (diff / 3_600_000).toInt())
        else -> dateLabel(time)
    }
}

fun dateLabel(time: Long): String = SimpleDateFormat("MMM yyyy", Locale.getDefault()).format(Date(time))

fun openDialer(context: Context, phone: String) {
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
    context.startActivity(intent)
}
