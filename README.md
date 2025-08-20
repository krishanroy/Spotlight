# 📰 Spotlight

**Spotlight** is a modern news app built with Jetpack Compose and Kotlin. It fetches real-time headlines
from [NewsAPI.org](https://newsapi.org/) and presents them in a clean, responsive UI. The app is designed
with scalability and maintainability in mind, using clean architecture principles and reactive state management.

> ⚠️ Work in progress — currently includes Home and Detail screens.

---

## ✨ Features

- 🔍 Browse latest headlines from NewsAPI
- 🧭 Navigate from Home screen to detailed article view
- 🧱 Built with Jetpack Compose for declarative UI
- ⚙️ Dependency Injection via Hilt
- 🔄 Reactive data flow using Kotlin Flow
- 📦 Modular architecture with clear separation of concerns

---

## 📸 Screenshots

*(Coming soon — UI still evolving)*

---

## 🧪 Tech Stack

| Layer        | Technology           |
|--------------|----------------------|
| UI           | Jetpack Compose      |
| DI           | Hilt                 |
| State        | Kotlin Flow          |
| Networking   | Retrofit + NewsAPI   |
| Language     | Kotlin               |
| Architecture | MVVM / Clean MVI     |

---

## 🗂️ Project Structure

```
spotlight/
├── data/           # API models, DTOs, repository
├── domain/         # Business models, use cases
├── ui/             # Compose screens and components
│   ├── home/       # Home screen
│   └── detail/     # Detail screen
├── di/             # Hilt modules
└── utils/          # Mappers, extensions, helpers
```

---

## 🚀 Getting Started

1. Clone the repo  
   ```bash
   git clone https://github.com/yourusername/spotlight.git
   ```

2. Get your NewsAPI key from [newsapi.org](https://newsapi.org/)

3. Add your API key to `local.properties`  
   ```
   NEWS_API_KEY=your_api_key_here
   ```

4. Build and run the app  
   ```bash
   ./gradlew installDebug
   ```

---

## 🧠 Roadmap

- [ ] Search functionality
- [ ] Category filters (e.g. Tech, Sports, Health)
- [ ] Bookmarking articles
- [ ] Offline caching
- [ ] Dark mode support
- [ ] Unit + UI tests

---

## 🤝 Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss what you’d like to change.

---

## 📄 License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.

---

## 🙌 Acknowledgments

- [NewsAPI.org](https://newsapi.org/) for the data
- Jetpack Compose team for pushing UI boundaries
