My submission for the Assessment from Emmanuel (I'm omitting the company name so that other applicants can't find this easily).

I've implemented the feature as requested:

1. Created a new project
2. Code written in Kotlin
3. Email and password login (with validation)
4. Persited Auth session using EncryptedSharedPreferences
5. Password Hash for security from reverse engineering
6. Logout button at the options menu
7. Every inventory item created is tied to the logged in user
8. A View Inventory Screen that shows the name, price, total stock and quantity of items. This also has an add button
9. An Add Inventory Screen with name, description, quantity and price validations.
10. An Edit and Delete Inventory Screen with name, description, quantity and price validations for editing the currently selected Inventory item.
11. Use navgraph for navigation
12. Use Hilt for Dependency Injection
13. Use clean architecture (data and ui packages)
14. Room for local sqlite storage
15. A comprehensive Integration test that runs through the end to end process of creating, viewing, editing, deleting and confirmation dialog test. The entry point for the complete e2e test is `AppInstrumentedTests.startInstrumentedTest()`

NB: I saw the assignment late and combined with my hectic weekend, I rushed this work. Total development time was approximately 6 hours. I intend to do the following within the next few days:
1. Localise the texts
2. Fix the commits. They were bulked due to the time constraints.
3. Use libs.version.toml (Version catalogs) to organise dependencies
4. Modularize the UI and data packages
5. Use a green theme for the app.
6. Implement the Password Hash using SHA256
