# Retail App

## Overview
Retail App is built following **Clean Architecture** with **feature-based modularization**, ensuring scalability, maintainability, and clear separation of concerns. The project follows the **MVI (Model–View–Intent)** pattern with strong state management principles and modern Android best practices, making it suitable for enterprise-grade production environments.

While the current scope focuses on product detail features, the architecture and codebase have been designed for **easy expansion** into a complete retail shopping experience.

---

## Screenshots

### Product Detail Screen
![Product Detail Screen](screenshots/product_detail_screenshot.png)

---

## Architecture Decisions

- **Clean Architecture** — Presentation, Domain, and Data layers are strictly separated for better testability, maintainability, and scalability.
- **Feature-Based Modularization** — Each feature (e.g., Product Detail) is isolated into its own module, enabling parallel development and reduced build times.
- **MVI Pattern** —  
  - **State**: Managed as a single immutable UI state object, ensuring predictable and testable state transitions.  
  - **Events (Intents)**: Represent user actions or system triggers, processed by the ViewModel.  
  - **Effects**: Used for one-time actions like navigation or showing a toast/snackbar.
- **Jetpack Compose UI** — Declarative, reactive UI built with reusable, theme-aware Composables.

---

## State and Interaction Management

The app uses **unidirectional data flow** to ensure reliability and maintainability:

- **UI State**: Stored in a single `StateFlow` per screen.
- **UI Effects**: Emitted via a `SharedFlow` for one-time actions (navigation, sharing, etc.).
- **Immutable State**: Prevents accidental mutations and ensures state is always predictable.

This approach guarantees seamless state restoration across configuration changes (e.g., screen rotation).

---

## Testing Strategy

The project maintains **strong test coverage** using **MockK**, **Google Truth**, and **Turbine**:

- **Unit Tests** — ViewModels, UseCases, and Repositories are fully covered, ensuring core logic correctness.

---

## UI & Theming

- Supports **Light** and **Dark** themes.
- Follows **Material 3** guidelines.
- **Localization-ready** — All text is stored in `strings.xml` for easy translation.
- Custom typography, colors, and dimens are centralized via a design system.

---

## Key Libraries & Tools

- **Jetpack Compose** — UI framework
- **Hilt** — Dependency injection
- **MockK + Google Truth + Turbine** — Testing stack
- **Coil** — Image loading
- **Timber** — Logging
- **LeakCanary** — Memory leak detection

---

## Production-Ready Enhancements

Improvements for a full-scale release:

- **Navigation**: Implement Compose Navigation with a well-defined navigation graph.
- **Offline Support**: Add caching with Room for offline-first behavior.
- **Analytics & Crash Reporting**: Add Firebase Analytics & Crashlytics.
- **Security**:
  - Enforce HTTPS
  - Certificate pinning
  - Play Integrity API
- **CI/CD**: Automated testing, linting, and deployment with GitHub Actions.
- **Network Layer**: Introduce a robust network layer for handling remote data sources using Retrofit or Ktor, with centralized error handling, logging, and request/response interceptors.

---

## Trade-offs & Decisions

- **Hilt over Koin**: Chosen for compile-time safety and official Jetpack support, despite more boilerplate.
- **MockK over Mockito**: Better Kotlin support and cleaner syntax, despite smaller community.

---

## Example Feature — Product Detail

The **Product Detail** feature demonstrates:

- Clean separation of UI, state, and effects.
- Strong unit test coverage.
- Composable reusability.
- Error handling with retry logic.
- Dynamic UI updates through immutable state.
