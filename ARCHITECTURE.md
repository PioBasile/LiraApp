# LiraApp Architecture Diagram

## Clean Architecture Layers

```
┌─────────────────────────────────────────────────────────────────┐
│                      PRESENTATION LAYER                          │
│  (UI, ViewModels, Navigation)                                   │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ┌──────────────┐  ┌──────────────┐  ┌─────────────────┐      │
│  │              │  │              │  │                 │      │
│  │  AuthScreen  │  │  HomeScreen  │  │ AddExpenseScreen│      │
│  │              │  │              │  │                 │      │
│  └──────┬───────┘  └──────┬───────┘  └────────┬────────┘      │
│         │                 │                    │                │
│         ▼                 ▼                    ▼                │
│  ┌──────────────┐  ┌──────────────┐  ┌─────────────────┐      │
│  │              │  │              │  │                 │      │
│  │AuthViewModel │  │HomeViewModel │  │AddExpenseViewModel│    │
│  │              │  │              │  │                 │      │
│  └──────┬───────┘  └──────┬───────┘  └────────┬────────┘      │
│         │                 │                    │                │
│         │       ┌─────────┴────────┐          │                │
│         │       │   MainActivity   │          │                │
│         │       │   + NavGraph     │          │                │
│         │       └──────────────────┘          │                │
└─────────┼─────────────────┼──────────────────┼─────────────────┘
          │                 │                   │
          │  Dependency Injection (Hilt)       │
          │                 │                   │
┌─────────┼─────────────────┼──────────────────┼─────────────────┐
│         │   DOMAIN LAYER  │                  │                 │
│         │   (Business Logic, Use Cases)      │                 │
│         ▼                 ▼                   ▼                 │
│  ┌──────────┐      ┌─────────────┐     ┌──────────┐           │
│  │ SignIn   │      │  GetMonthly │     │   Add    │           │
│  │ UseCase  │      │   Summary   │     │ Expense  │           │
│  └────┬─────┘      │  UseCase    │     │ UseCase  │           │
│       │            └──────┬──────┘     └────┬─────┘           │
│  ┌────┴─────┐            │                  │                 │
│  │ SignUp   │            │                  │                 │
│  │ UseCase  │            │                  │                 │
│  └────┬─────┘            │                  │                 │
│       │                  │                  │                 │
│       │            ┌─────┴─────┐     ┌──────┴──────┐          │
│       │            │GetExpenses│     │GetCurrentUser│         │
│       │            │ByMonth    │     │  UseCase    │          │
│       │            │ UseCase   │     └─────────────┘          │
│       │            └───────────┘                               │
│       │                  │                  │                 │
│       ▼                  ▼                  ▼                 │
│  ┌──────────────────────────────────────────────────┐        │
│  │         Repository Interfaces (Contracts)         │        │
│  │  ┌─────────────────┐   ┌─────────────────────┐  │        │
│  │  │  AuthRepository │   │  ExpenseRepository  │  │        │
│  │  │   (interface)   │   │    (interface)      │  │        │
│  │  └─────────────────┘   └─────────────────────┘  │        │
│  └──────────────────────────────────────────────────┘        │
│                                                                │
│  ┌──────────────────────────────────────────────────┐        │
│  │              Domain Models                        │        │
│  │  ┌─────────┐ ┌──────────┐ ┌────────┐ ┌────────┐ │        │
│  │  │ Expense │ │ Category │ │  User  │ │Summary │ │        │
│  │  └─────────┘ └──────────┘ └────────┘ └────────┘ │        │
│  └──────────────────────────────────────────────────┘        │
└─────────────────────────┬──────────────────────────────────────┘
                          │
┌─────────────────────────┴──────────────────────────────────────┐
│                       DATA LAYER                                │
│               (Data Sources, Implementations)                   │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ┌──────────────────────────────────────────────────┐          │
│  │       Repository Implementations                  │          │
│  │  ┌──────────────────┐  ┌─────────────────────┐  │          │
│  │  │AuthRepositoryImpl│  │ExpenseRepositoryImpl│  │          │
│  │  └────────┬─────────┘  └──────────┬──────────┘  │          │
│  └───────────┼────────────────────────┼─────────────┘          │
│              │                        │                         │
│              ▼                        ▼                         │
│  ┌───────────────────────────────────────────────┐             │
│  │          SupabaseService                      │             │
│  │  ┌────────────┐  ┌─────────────────────────┐ │             │
│  │  │ Auth APIs  │  │  Postgrest APIs         │ │             │
│  │  │ - signUp   │  │  - insertExpense        │ │             │
│  │  │ - signIn   │  │  - updateExpense        │ │             │
│  │  │ - signOut  │  │  - deleteExpense        │ │             │
│  │  │ - getUser  │  │  - getExpensesByMonth   │ │             │
│  │  └────────────┘  └─────────────────────────┘ │             │
│  └───────────────────────────────────────────────┘             │
│                                                                  │
│  ┌───────────────────────────────────────────────┐             │
│  │          Data Transfer Objects                 │             │
│  │  ┌─────────────┐  ┌──────────────────────┐   │             │
│  │  │ ExpenseDto  │  │     Mapper           │   │             │
│  │  │ (with @     │◄─┤ toDto() / toDomain()│   │             │
│  │  │ Serializable)│  └──────────────────────┘   │             │
│  │  └─────────────┘                               │             │
│  └───────────────────────────────────────────────┘             │
└──────────────────────────┬──────────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────────┐
│                    EXTERNAL SERVICES                             │
│                                                                  │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │                    SUPABASE                              │   │
│  │  ┌──────────────────┐         ┌──────────────────────┐ │   │
│  │  │  PostgreSQL DB   │         │   Supabase Auth      │ │   │
│  │  │                  │         │                      │ │   │
│  │  │  expenses table  │         │  User Management     │ │   │
│  │  │  - id            │         │  JWT Tokens          │ │   │
│  │  │  - user_id       │         │  Session Management  │ │   │
│  │  │  - amount        │         └──────────────────────┘ │   │
│  │  │  - category      │                                   │   │
│  │  │  - description   │         ┌──────────────────────┐ │   │
│  │  │  - date          │         │   Row Level Security │ │   │
│  │  │  - created_at    │         │   (RLS Policies)     │ │   │
│  │  │                  │         └──────────────────────┘ │   │
│  │  └──────────────────┘                                   │   │
│  └─────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
```

## Data Flow Examples

### Adding an Expense

```
User Input (AddExpenseScreen)
    ↓
AddExpenseViewModel.addExpense()
    ↓
AddExpenseUseCase.invoke()
    ↓
ExpenseRepository.addExpense()
    ↓
ExpenseRepositoryImpl.addExpense()
    ↓
SupabaseService.insertExpense()
    ↓
Supabase API (Postgrest)
    ↓
PostgreSQL Database
    ↓
[Success/Error flows back up]
    ↓
UI Update
```

### Getting Monthly Summary

```
HomeScreen loads
    ↓
HomeViewModel.loadMonthlySummary()
    ↓
GetMonthlySummaryUseCase.invoke()
    ↓
ExpenseRepository.getExpensesByMonth() [Flow]
    ↓
ExpenseRepositoryImpl.getExpensesByMonth()
    ↓
SupabaseService.getExpensesByMonth()
    ↓
Supabase API → Database Query
    ↓
Map to Domain Models
    ↓
Calculate Category Totals
    ↓
Emit MonthlySummary via Flow
    ↓
HomeViewModel updates State
    ↓
HomeScreen recomposes with new data
```

### User Authentication

```
AuthScreen - User enters credentials
    ↓
AuthViewModel.signIn(email, password)
    ↓
SignInUseCase.invoke()
    ↓
AuthRepository.signIn()
    ↓
AuthRepositoryImpl.signIn()
    ↓
SupabaseService.signIn()
    ↓
Supabase Auth API
    ↓
JWT Token + User Session
    ↓
Store in AuthRepository
    ↓
Navigate to HomeScreen
```

## Dependency Injection Structure (Hilt)

```
┌──────────────────────────────┐
│    @HiltAndroidApp           │
│    LiraApplication           │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│    @InstallIn(SingletonScope)│
│    AppModule                  │
├──────────────────────────────┤
│ @Provides                     │
│ - SupabaseClient             │
│ - SupabaseService            │
│ - AuthRepository             │
│ - ExpenseRepository          │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│    @AndroidEntryPoint        │
│    MainActivity              │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│    @HiltViewModel            │
│    ViewModels                │
│    (inject UseCases)         │
└──────────────────────────────┘
```

## Key Design Patterns

1. **Clean Architecture**: Separation into Domain, Data, and Presentation layers
2. **MVVM**: ViewModels manage UI state and business logic
3. **Repository Pattern**: Abstract data sources behind interfaces
4. **Dependency Injection**: Hilt for managing dependencies
5. **Use Case Pattern**: Encapsulate business operations
6. **Observer Pattern**: Flow for reactive data streams
7. **Strategy Pattern**: Different implementations via interfaces

## Navigation Flow

```
App Start
    │
    ▼
Check Auth Status
    │
    ├─[Not Logged In]──► AuthScreen
    │                        │
    │                        ▼
    │                   Sign In/Up Success
    │                        │
    └─[Logged In]────────────┴──► HomeScreen
                                      │
                                      ├──► [View Expenses]
                                      │
                                      └──► [Tap FAB]
                                           │
                                           ▼
                                      AddExpenseScreen
                                           │
                                           ▼
                                     [Submit Success]
                                           │
                                           ▼
                                      Back to HomeScreen
```

## Technology Stack Visualization

```
┌─────────────────────────────────────────────┐
│          User Interface                      │
│  Jetpack Compose + Material 3               │
└─────────────────┬───────────────────────────┘
                  │
┌─────────────────┴───────────────────────────┐
│          Presentation Logic                  │
│  ViewModels + Jetpack Navigation            │
└─────────────────┬───────────────────────────┘
                  │
┌─────────────────┴───────────────────────────┐
│          Business Logic                      │
│  Use Cases + Domain Models                  │
└─────────────────┬───────────────────────────┘
                  │
┌─────────────────┴───────────────────────────┐
│          Data Management                     │
│  Repositories + Data Sources                │
└─────────────────┬───────────────────────────┘
                  │
┌─────────────────┴───────────────────────────┐
│          Network Layer                       │
│  Supabase SDK + Ktor Client                │
└─────────────────┬───────────────────────────┘
                  │
┌─────────────────┴───────────────────────────┐
│          Backend Services                    │
│  Supabase (Auth + PostgreSQL)              │
└─────────────────────────────────────────────┘

Cross-cutting Concerns:
├── Dependency Injection (Hilt)
├── Coroutines & Flow (Async)
├── Lifecycle Management (AndroidX)
└── Error Handling (Result<T>)
```
