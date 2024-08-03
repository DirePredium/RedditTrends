# RedditTrends

Displays top Reddit posts using https://www.reddit.com/dev/api. Ability to save a full photo of a post to the gallery

## Demo
![app_screen2](https://github.com/user-attachments/assets/998fdb76-b689-49ef-96bf-1ab04a025c5d)
![app_screen1](https://github.com/user-attachments/assets/140c3984-8e88-41ce-9bf1-4d6bdcae46bd)

## Tech Stack

- Android.minSdk = 24

- Android.targetSdk = 34
---
- Architectural pattern=MVVM
---
Libs:
- Android Jetpack's Navigation
- Hilt
- Retrofit
- swiperefreshlayout
- Paging
- Glide

## Internal construction
```
|-- data
    |-- api
        |-- retrofit
    |-- models // data models that match data obtained from the network or storage
    |-- repository // domain.repositories implementation
        |-- FileRepositoryImpl
        |-- PostsRepositoryImpl
        |-- PostStateRepositoryImpl
    |-- storage
        |-- local
            |-- FileLocalStorage // FileStorage implementation
        |-- sharedprefs
            |-- SharedPrefPostStateStorage // PostStateStorage implementation
        |-- FileStorage
        |-- PostStateStorage
    |-- util // auxiliary functions (utilities) for data conversion, formatting, etc.
|-- di // module for Dependency Injection to manage dependencies in an application
|-- domain
    | model // data models used in business logic
    | repository
        |-- FileRepository
        |-- PostsRepository
        |-- PostStateRepository
    | usecase // business logic, application use cases interacting with repositories
        |-- GetPostsByPageUseCase
        |-- SaveWebPhotoToGalleryUseCase
        |-- GetPostStateUseCase
        |-- SavePostStateUseCase
|-- presentor
    | adapters
        |-- PostsAdapter
        |-- PostsLoadStateAdapter
    | paging // PagingSource implementation
        |-- PostsPagingSource
    | screens // fragments
    | viewmodel
    | MainActivity.kt // _screens_ holder, *Single Activity*
|-- App.kt
```
