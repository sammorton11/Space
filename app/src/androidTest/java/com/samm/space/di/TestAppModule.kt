package com.samm.space.di

//@TestInstallIn(
//    components = [SingletonComponent::class],
//    replaces = [AppModule::class]
//)
//@Module
//object TestAppModule {
//
//    // Api providers are not actually called -- Hilt throws errors without them
//    @Provides
//    @Singleton
//    fun provideRepository(): MediaLibraryRepository {
//        return FakeMediaLibraryRepository()
//    }
//
//    @Provides
//    @Singleton
//    fun provideApodRepositorySuccess(): ApodRepository {
//        return FakeApodRepository()
//    }
//
//    @Provides
//    @Singleton
//    fun provideMetaApi(): MetadataApi {
//        val gson = GsonBuilder().setLenient().create()
//        return Retrofit.Builder()
//            .baseUrl(Constants.BASE_URL)
//            .addConverterFactory(ScalarsConverterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//            .create(MetadataApi::class.java)
//    }
//
//    @Provides
//    @Singleton
//    fun provideNasaApi(): NasaApi {
//        return Retrofit.Builder()
//            .baseUrl(Constants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(NasaApi::class.java)
//    }
//}