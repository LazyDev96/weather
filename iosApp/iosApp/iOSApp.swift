import SwiftUI
import Shared

@main
struct iOSApp: App {
    init() {
        // Граф зависимостей (Koin) должен быть готов до первого экрана
        MainViewControllerKt.startKoinIos()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
