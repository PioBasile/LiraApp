# LiraApp - Expense Tracking Android App

LiraApp is a Kotlin Android application for tracking personal expenses with clean architecture and Supabase backend integration.

## Features

- **User Authentication**: Sign up and sign in functionality using Supabase Auth
- **Expense Tracking**: Add, view, and categorize expenses
- **Category Management**: Multiple expense categories (Food, Transport, Entertainment, Shopping, Bills, Health, Education, Other)
- **Monthly Summary**: View expenses by month with category breakdown
- **Clean Architecture**: Separation of concerns with Domain, Data, and Presentation layers

## Architecture

The app follows Clean Architecture principles with three main layers:

### Domain Layer
- **Models**: Expense, User, ExpenseCategory, MonthlySummary
- **Use Cases**: AddExpenseUseCase, GetExpensesByMonthUseCase, GetMonthlySummaryUseCase, SignInUseCase, SignUpUseCase
- **Repositories**: Interfaces for AuthRepository and ExpenseRepository

### Data Layer
- **Remote**: Supabase integration for data persistence
- **Repository Implementations**: AuthRepositoryImpl, ExpenseRepositoryImpl
- **DTOs**: Data transfer objects for API communication

### Presentation Layer
- **Screens**: Auth, Home, AddExpense
- **ViewModels**: Handle UI logic and state management
- **Navigation**: Jetpack Compose Navigation

## Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose with Material 3
- **Architecture**: Clean Architecture with MVVM
- **Dependency Injection**: Hilt
- **Backend**: Supabase (PostgreSQL + Auth)
- **Async**: Kotlin Coroutines & Flow
- **Navigation**: Jetpack Compose Navigation

## Setup

### Prerequisites
- Android Studio Hedgehog or newer
- JDK 17
- Android SDK with API level 24+

### Supabase Configuration

1. Create a Supabase project at [supabase.com](https://supabase.com)
2. Create an `expenses` table with the following schema:

```sql
CREATE TABLE expenses (
  id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
  user_id UUID NOT NULL REFERENCES auth.users(id),
  amount DECIMAL NOT NULL,
  category TEXT NOT NULL,
  description TEXT NOT NULL,
  date BIGINT NOT NULL,
  created_at BIGINT NOT NULL
);

-- Add RLS policies
ALTER TABLE expenses ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Users can insert their own expenses"
  ON expenses FOR INSERT
  WITH CHECK (auth.uid() = user_id);

CREATE POLICY "Users can view their own expenses"
  ON expenses FOR SELECT
  USING (auth.uid() = user_id);

CREATE POLICY "Users can update their own expenses"
  ON expenses FOR UPDATE
  USING (auth.uid() = user_id);

CREATE POLICY "Users can delete their own expenses"
  ON expenses FOR DELETE
  USING (auth.uid() = user_id);
```

3. Update the Supabase credentials in `app/src/main/java/com/liraapp/expense/di/AppModule.kt`:
   - Replace `your-project.supabase.co` with your Supabase project URL
   - Replace `your-anon-key` with your Supabase anon key

### Building the Project

```bash
./gradlew build
```

### Running the App

1. Open the project in Android Studio
2. Update Supabase credentials as mentioned above
3. Sync Gradle files
4. Run the app on an emulator or physical device

## Usage

1. **Sign Up / Sign In**: Create an account or sign in with existing credentials
2. **View Dashboard**: See your monthly expenses with category breakdown
3. **Add Expense**: Tap the + button to add a new expense with amount, category, and description
4. **Browse History**: Navigate through different months to view past expenses

## License

This project is licensed under the MIT License - see the LICENSE file for details.