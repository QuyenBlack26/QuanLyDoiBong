# Walkthrough - User Display and Persistence

I have implemented the logic to display the user's name and role across all screens and ensure the login state persists.

## Changes Made

### 1. Centralized User Header
- Created `layout_user_header.xml` to consistently show the user's full name and role.
- Developed `UserHeaderUtils.kt` to handle the logic of fetching data from the session and updating the UI.

### 2. Universal Visibility
- Added the user header to **all main screens**:
    - Home (HomeNewActivity)
    - Team Detail & History (HomeActivity)
    - Football News (MainActivity)
    - Player Management (PlayerManagementActivity)
    - Add/View News (AddNewsActivity, NewsDetailActivity)

### 3. Login Persistence
- Updated the main entry point (`HomeNewActivity`) to check `SessionManager` on every startup. This means if you have logged in before, the app will remember you and show your info immediately without needing to log in again.

### 4. Logout Feature
- Added a click listener to the user header. Clicking on your name/role will now open a dialog allowing you to **Logout**.

## Verification Results

### Automated Tests
- Build successfully completed (`assembleDebug`).

### Manual Verification (Instructions)
1. **Log in**: Open the app and log in as `admin`.
2. **Browse**: Navigate through "Tin tức", "Chi tiết", and "Quản lý". You should see your name and "Vai trò: Admin" at the top right of every screen.
3. **Restart App**: Close the app completely and reopen it. You should still see your information on the home screen (persisted state).
4. **Logout**: Click on your name at the top right, select **"Đăng xuất"**, and verify you are redirected to the login screen and the session is cleared.

> [!IMPORTANT]
> To ensure the user info is always visible, I've integrated it into the `MaterialToolbar` or positioned it at the top of each layout.

> [!TIP]
> The role and name are fetched directly from the local database session, ensuring they are always up-to-date with your account details.
