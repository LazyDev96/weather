import SwiftUI
import Shared

/// Мост между SwiftUI и общим Compose-интерфейсом из модуля :shared.
struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
            // Отступы под вырез и жесты учитывает сам Compose
            .ignoresSafeArea()
    }
}
