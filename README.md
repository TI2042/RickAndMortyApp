
# Rick And Morty App

Проект реализован на **Jetpack Compose**, поддерживает фильтрацию, просмотр деталей, кэширование, поиск и современный UI.

---

## 📱 Возможности

- Список персонажей с постраничной загрузкой (пагинация)
- Фильтрация по статусу, полу, виду и поиску по имени
- Детальный просмотр персонажа (аватар, статус, вид, пол и пр.)
- Кэширование данных в локальной базе (Room)
- Автоматическая подгрузка информации о персонаже из сети при первом просмотре
- MVVM-архитектура

---

## Используемые технологии

- **Kotlin**
- **Jetpack Compose** (UI)
- **Navigation Compose** (навигация между экранами)
- **Room** (локальная база данных)
- **Retrofit** (работа с API)
- **Kotlin Coroutines, Flow, StateFlow** (асинхронность и реактивность)
- **MVVM** (разделение UI, логики и данных)
- **Coil** (загрузка изображений)
- **Mockk, JUnit** (юнит-тесты)
- **TypeConverters** (enum String для Room)
- **Material Design 3** (UI)

---

## Архитектура

**MVVM (Model-View-ViewModel):**
- **View:** Composable-экраны, NavHost, UI-компоненты
- **ViewModel:** Логика, состояние, фильтры, пагинация, загрузка данных
- **Model:** Entity, Enum, DTO
- **Repository:** Вся работа с источниками данных (Room, Retrofit)
- **Data Source:** API, БД (Room)
- **DI (через remember, можно добавить Hilt/Koin)**

---

## Как запустить

1. Склонировать репозиторий
2. Открыть в Android Studio
3. Собрать и запустить (требуется интернет для первого запуска)

---

## Тестирование

- Покрытие unit-тестами репозитория и ViewModel
- Используются [mockk](https://mockk.io/) и [kotlinx.coroutines.test](https://github.com/Kotlin/kotlinx.coroutines)

---

## Автор

- Тягин Игорь Сергеевич
- [GitHub](https://github.com/TI2042/RickAndMortyApp)
- [Резюме](https://hh.ru/resume/80d1c78bff0b5924810039ed1f583054664454)
