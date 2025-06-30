import SwiftUI
import shared

@main
struct iOSApp: App {
    
    init(){
        MainModuleKt.doInitKoin()
    }
    
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
