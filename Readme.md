# Todo App — Android Native

> _A modern Android todo application built with Kotlin, Jetpack Compose, Hilt, and Room._

---

## Architecture

This project follows **Clean Architecture** with 3 layers:

```
presentation/ → domain/ ← data/
```

| Layer | Responsibility |
|---|---|
| `presentation` | UI (Compose), ViewModel, UiState |
| `domain` | Business logic, UseCases, Repository interfaces |
| `data` | Room, DataSources, Repository implementations |

### Dependency flow
```
UI → ViewModel → UseCase → Repository (interface)
                                ↓
                    LocalDataSource | RemoteDataSource (future)
```

---

## Project Structure

```
app/src/main/
├── core/
│   └── util/
│       ├── Result.kt           # Sealed interface for success/error handling
│       └── RootError.kt        # Marker interface for all errors
│
├── data/
│   ├── local/
│   │   ├── TaskEntity.kt       # Room entity
│   │   ├── TaskDao.kt          # Room DAO
│   │   ├── TaskMapper.kt       # TaskEntity ↔ Task (domain)
│   │   ├── AppDatabase.kt      # Room database
│   │   └── LocalError.kt       # Data layer errors + toTaskError() mapper
│   ├── datasource/
│   │   ├── TaskLocalDataSource.kt      # Interface
│   │   └── TaskLocalDataSourceImpl.kt  # Room implementation
│   ├── repository/
│   │   └── TaskRepositoryImpl.kt
│   └── di/
│       ├── DatabaseModule.kt       # Provides AppDatabase, TaskDao
│       ├── DataSourceModule.kt     # Binds DataSource implementations
│       └── RepositoryModule.kt     # Binds Repository implementations
│
├── domain/
│   ├── model/
│   │   └── Task.kt             # Business model
│   ├── error/
│   │   └── TaskError.kt        # Domain errors (DatabaseError, NotFound, validation...)
│   ├── repository/
│   │   └── TaskRepository.kt   # Interface
│   └── usecase/
│       └── ...                 # TODO: one file per action
│
└── presentation/
    ├── ui/                     # TODO: Composables
    └── viewmodel/              # TODO: ViewModels + UiState
```

---

## Tech Stack

| Technology | Usage |
|---|---|
| Kotlin | Language |
| Jetpack Compose | UI |
| Hilt | Dependency injection |
| Room | Local database |
| Kotlin Coroutines | Async operations |
| Kotlin Flow | Reactive streams |
| kotlinx-datetime | Date/time handling |
| Navigation 3 (nav3) | Navigation — _planned_ |

---

## Data Flow

### Read (reactive)
```
Room (Flow<TaskEntity>) 
  → TaskLocalDataSourceImpl (map + catch → Flow<Result<Task, LocalError>>)
  → TaskRepositoryImpl (map errors → Flow<Result<Task, TaskError>>)
  → UseCase
  → ViewModel (→ UiState)
  → Compose UI
```

### Write (suspend)
```
UI action
  → ViewModel
  → UseCase (validation → TaskError if invalid)
  → TaskRepositoryImpl (Result<Unit, TaskError>)
  → TaskLocalDataSourceImpl (try/catch → Result<Unit, LocalError>)
  → TaskDao (Room)
```

---

## Error Handling

Errors are typed and propagated via `Result<D, E : RootError>` — never thrown silently.

```
Room exception (SQLiteException)
  → caught in TaskLocalDataSourceImpl
  → LocalError (DatabaseError | NotFound)
  → mapped to TaskError in TaskRepositoryImpl
  → TaskError (DatabaseError | NotFound | TitleTooLong | DescriptionTooLong)
  → UiState.Error in ViewModel
```

**Rule:** Only `TaskLocalDataSourceImpl` catches exceptions. All other layers speak `Result`.

---

## Features

### Implemented
- [ ] Local task storage (Room)
- [ ] Create / Read / Update / Delete tasks

### In progress
- [ ] UseCases (validation, business logic)
- [ ] ViewModel + UiState
- [ ] Compose UI (following mockup)

### Planned
- [ ] Filter by due date (Today / Past / Future / Done)
- [ ] Sort (creation date, due date, alphabetical, manual drag & drop)
- [ ] Manual sort with persistent `position` field
- [ ] SubTask support (Room migration v2)
- [ ] Category support (Room migration v3)
- [ ] Login / Authentication
- [ ] Remote database sync (offline-first)
- [ ] Modularization
- [ ] Navigation 3 (nav3)

---

## 🗃️ Database

### Current schema (v1)
| Table | Fields |
|---|---|
| `tasks` | id (String/UUID), title, description, is_completed, created_at (Long), due_date (Long?), category_id (String?), position (Int) |

### Migration plan
| Version | Change |
|---|---|
| v1 | Initial — tasks table |
| v2 | _TODO: Add subtasks table_ |
| v3 | _TODO: Add categories table (with color, hidden fields)_ |

---

## 📏 Business Rules

- Task title: max **300 characters**
- Task description: max **500 characters**
- Default due date: **today** if none selected
- Task ID: **UUID** (client-generated for offline-first support)
- Dates stored as **epoch milliseconds** (`Long`) in Room, converted to `kotlinx.datetime` in domain

---

## Future Architecture (Modularization)

- A feature-based modularization is planned for the future when new features will be added such as Calendar, Dashboard, etc.
- For now, everything is in the app package for simplicity and to do the exercise of modularizing which often happens in real projects.

---

## TODO for this Todo-list app

- [ ] Write UseCases
- [ ] Implement ViewModels
- [ ] Build Compose UI from mockup
- [ ] Build Design System
- [ ] Add input validation in UseCases
- [ ] Handle CancellationException policy across layers
- [ ] Add Room migrations for SubTask and Category
- [ ] Set up remote DataSource (Retrofit)
- [ ] Add login flow
- [ ] Set up nav3