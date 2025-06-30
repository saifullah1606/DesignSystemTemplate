import SwiftUI
import Foundation
import shared

/**
 * The main SwiftUI View for displaying categories on iOS.
 * It observes the [iOSPlatformViewModel] to react to changes in the category data state.
 */
struct ContentView: View {
    // Observe the ViewModel for UI updates.
    // [iOSPlatformViewModel] wraps the shared Kotlin [StoreFrontViewModel].
    @ObservedObject private var viewModel = iOSPlatformViewModel()

    var body: some View {
        NavigationView {
            VStack {
                // Display UI based on the current state of categories.
                switch viewModel.uiState {
                case shared.StoreFrontUiStateLoading: // Explicitly cast for clearer type inference
                    ProgressView("Loading store front data...")
                        .progressViewStyle(CircularProgressViewStyle())
                        .onAppear {
                            print("DEBUG: UI State is Loading. Type: \(type(of: loading)). Value: \(loading)")
                        }
                case let success as shared.StoreFrontUiStateSuccess: // Explicitly cast
                    if success.mainCategories.isEmpty {
                        Text("No main categories found.")
                            .font(.headline)
                            .padding()
                    } else {
                        List {
                            ForEach(success.mainCategories, id: \.id) { mainCategory in
                                MainCategoryRow(mainCategory: mainCategory)
                            }
                        }
                        .listStyle(PlainListStyle())
                    }
                    
                case let error as shared.StoreFrontUiStateError: // Explicitly cast
                    VStack {
                        Image(systemName: "exclamationmark.triangle.fill")
                            .resizable()
                            .aspectRatio(contentMode: .fit)
                            .frame(width: 50, height: 50)
                            .foregroundColor(.red)
                            .padding(.bottom, 10)
                        Text(error.message)
                            .foregroundColor(.red)
                            .multilineTextAlignment(.center)
                            .padding(.horizontal)
                        Button("Retry") {
                            viewModel.loadStoreFrontData() // Allow retrying on error
                        }
                        .padding()
                        .background(Color.blue)
                        .foregroundColor(.white)
                        .cornerRadius(10)
                        .padding(.top, 10)
                    }
                    .onAppear {
                        print("DEBUG: UI State is Error. Type: \(type(of: error)). Message: \(error.message)")
                    }
                default:
                    Text("Unknown state")
                        .onAppear {
                            print("DEBUG: Unknown state encountered. Actual uiState type: \(type(of: viewModel.uiState))")
                            // You can also try to print the value, though for complex objects it might not be very readable
                            // print("DEBUG: Unknown uiState value: \(viewModel.uiState)")
                        }
                }
            }
            .navigationTitle("Ktor KMM Store Front")
            .onAppear {
                // On initial appearance, ensure categories are loaded.
                // This is redundant if already called in ViewModel init, but good for explicit control.
                print("DEBUG: ContentView appeared. Calling loadStoreFrontData.")
                viewModel.loadStoreFrontData()
            }
        }
    }
}

/**
 * A SwiftUI View to display a single MainCategory row within the list.
 * This view allows expanding to show subcategories.
 */
struct MainCategoryRow: View {
    let mainCategory: shared.MainCategory
    @State private var isExpanded: Bool = false

    var body: some View {
        VStack(alignment: .leading) {
            HStack {
                VStack(alignment: .leading) {
                    Text(mainCategory.name)
                        .font(.headline)
                        .foregroundColor(.primary)
                    if let deepLink = mainCategory.deepLink {
                        Text("Link: \(deepLink)")
                            .font(.caption)
                            .foregroundColor(.secondary)
                    }
                }
                Spacer()
                if let subCategories = mainCategory.subCategoryList, !subCategories.isEmpty {
                    Image(systemName: isExpanded ? "chevron.up" : "chevron.down")
                        .onTapGesture {
                            withAnimation {
                                isExpanded.toggle()
                            }
                        }
                }
            }
            .padding(.vertical, 5)

            if isExpanded, let subCategories = mainCategory.subCategoryList, !subCategories.isEmpty {
                Divider()
                VStack(alignment: .leading, spacing: 4) {
                    ForEach(subCategories, id: \.id) { subCategory in
                        SubCategoryItem(subCategory: subCategory)
                    }
                }
                .padding(.leading, 15)
                .padding(.vertical, 5)
            }
        }
    }
}

/**
 * A SwiftUI View to display a single SubCategory item.
 */
struct SubCategoryItem: View {
    let subCategory: shared.SubCategory

    var body: some View {
        VStack(alignment: .leading) {
            Text(subCategory.name ?? "Unnamed Subcategory")
                .font(.subheadline)
                .foregroundColor(.secondary)
            if let description = subCategory.description_ { // Note: 'description' is a reserved keyword in Swift, so KMM generates 'description_'
                Text(description)
                    .font(.caption2)
                    .foregroundColor(.gray)
            }
            if let cards = subCategory.cards, !cards.isEmpty {
                Text("Cards: \(cards.count)")
                    .font(.caption2)
                    .foregroundColor(.blue)
            }
        }
    }
}


// MARK: - Preview Provider
struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}


// struct ContentView: View {
// 	let greet = Greeting().greet()
// 	let theme = ThemeKt.getTheme(scheme: .light)
//
// 	var body: some View {
// 		Text(greet)
//             .font(.system(size: CGFloat(theme.typography.h2.fontSize)))
//             .foregroundColor(Color(hex: theme.colors.secondary))
// 	}
// }
//
// struct ContentView_Previews: PreviewProvider {
// 	static var previews: some View {
// 		ContentView()
// 	}
// }
//
//
// extension Color {
//     init(hex: Int64) {
//         let red = Double((hex >> 16) & 0xff) / 255
//         let green = Double((hex >> 8) & 0xff) / 255
//         let blue = Double(hex & 0xff) / 255
//         let alpha = Double((hex >> 24) & 0xff) / 255
//         self.init(.sRGB, red: red, green: green, blue: blue, opacity: alpha)
//     }
// }




/**
 * A wrapper class for the shared Kotlin [StoreFrontViewModel] to make it compatible with SwiftUI.
 * It conforms to [ObservableObject] so that SwiftUI views can react to its changes.
 */
class iOSPlatformViewModel: ObservableObject {
    // The instance of the shared Kotlin ViewModel.
    private let viewModel: StoreFrontViewModel

    // Published property to expose the UI state to SwiftUI views.
    // When the Kotlin ViewModel's uiState changes, this property will be updated,
    // triggering redraws.
    @Published var uiState: StoreFrontUiState = shared.StoreFrontUiStateLoading()

    /**
     * Initializes the wrapper ViewModel.
     * It creates the shared Kotlin ViewModel using the platform-specific factory
     * and sets up a collector for its uiState.
     */
    init() {
        // Create an instance of the Kotlin StoreFrontViewModel using the ViewModelFactory.
        // This ensures the correct platform-specific Ktor engine is used (Darwin for iOS).
        viewModel = ViewModelFactory().createStoreFrontViewModel()

        // Observe the uiState from the Kotlin ViewModel using a Task that runs on the MainActor.
        // Calling Kotlin suspend functions from Swift/Objective-C requires them to be on the main thread.
        Task { @MainActor in // <--- CHANGE HERE: Run the Task on the MainActor
            do {
                try await viewModel.uiState.collect(collector: FlowCollector<StoreFrontUiState> { state in
                    // Ensure UI updates happen on the main thread as @Published properties should be updated on main.
                    // This DispatchQueue.main.async is still good practice even with @MainActor on the Task,
                    // especially if `state` is complex and its processing might block the main thread.
                    DispatchQueue.main.async {
                        self.uiState = state
                    }
                })
            } catch {
                // Handle any errors that occur during collection, e.g., print them or update UI state to Error.
                print("Error collecting UI state: \(error)")
                DispatchQueue.main.async {
                    self.uiState = shared.StoreFrontUiStateError(message: "Failed to load data: \(error.localizedDescription)")
                }
            }
        }
    }

    /**
     * Exposes the `loadStoreFrontData` function from the Kotlin ViewModel to Swift/SwiftUI.
     * Allows SwiftUI views to trigger data reloading.
     */
    func loadStoreFrontData() {
        viewModel.loadStoreFrontData()
    }

    /**
     * Clears the ViewModel when it's no longer needed, cancelling its coroutines.
     * This is important for resource management and preventing memory leaks.
     */
    deinit {
        viewModel.clear()
    }
}

// Helper protocol and class to bridge Kotlinx Flow to Swift.
// This is typically provided by KMM templates or a common library.
// For simplicity, we define it here.
private class FlowCollector<T>: Kotlinx_coroutines_coreFlowCollector {
    let callback: (T) -> Void

    init(callback: @escaping (T) -> Void) {
        self.callback = callback
    }

    func emit(value: Any?, completionHandler: @escaping (Error?) -> Void) {
        if let value = value as? T {
            callback(value)
        }
        completionHandler(nil)
    }
}
