# Implementation Plan - Display User Info and Persist Login State

The goal is to ensure the user's role and name are displayed on all pages and that the login state is maintained across app restarts.

## User Review Required

> [!NOTE]
> I will add a user info header to all main pages. I will also add a "Logout" functionality (accessible by clicking the user info) to allow users to sign out.

## Proposed Changes

### 1. Create Reusable User Info Layout
#### [NEW] [layout_user_header.xml](file:///D:/Downloads/GITHUB/QuanLyDoiBong/app/src/main/res/layout/layout_user_header.xml)
- Contains `tvUserName` and `tvUserRole` in a `LinearLayout`.

### 2. Update Activity Layouts
Include the new `layout_user_header.xml` in the following files:
#### [MODIFY] [activity_home_new.xml](file:///D:/Downloads/GITHUB/QuanLyDoiBong/app/src/main/res/layout/activity_home_new.xml)
#### [MODIFY] [activity_main.xml](file:///D:/Downloads/GITHUB/QuanLyDoiBong/app/src/main/res/layout/activity_main.xml)
#### [MODIFY] [activity_home.xml](file:///D:/Downloads/GITHUB/QuanLyDoiBong/app/src/main/res/layout/activity_home.xml)
#### [MODIFY] [activity_player_management.xml](file:///D:/Downloads/GITHUB/QuanLyDoiBong/app/src/main/res/layout/activity_player_management.xml)

### 3. Create UI Helper for User Info
#### [NEW] [UserHeaderUtils.kt](file:///D:/Downloads/GITHUB/QuanLyDoiBong/app/src/main/java/com/example/quanli/UserHeaderUtils.kt)
- Create a function `setupUserHeader(activity: Activity)` that:
    - Checks `SessionManager`.
    - Updates `tvUserName` and `tvUserRole`.
    - Handles visibility.
    - Adds a click listener for logout/profile options.

### 4. Update Activities Logic
Call `UserHeaderUtils.setupUserHeader(this)` in:
- `HomeNewActivity.kt`
- `MainActivity.kt`
- `HomeActivity.kt`
- `PlayerManagementActivity.kt`

### 5. Fix Login Persistence
#### [MODIFY] [HomeNewActivity.kt](file:///D:/Downloads/GITHUB/QuanLyDoiBong/app/src/main/java/com/example/quanli/HomeNewActivity.kt)
- Update `onCreate` to check `SessionManager.isLoggedIn()` instead of relying solely on Intent extras.

## Verification Plan

### Automated Tests
- Build the project to ensure no layout or code errors.

### Manual Verification
1. **Login**: Log in as a user (e.g., `admin`).
2. **Persistence**: Close the app completely and reopen it. Verify that you are still logged in and your name/role are displayed on the home screen.
3. **Consistency**: Navigate to News, Detail, History, and Management. Verify that the user header is visible and shows the correct information on all these pages.
4. **Logout**: Click on the user header and confirm logout. Verify that the app redirects to the Login screen or resets to "Guest" mode.
