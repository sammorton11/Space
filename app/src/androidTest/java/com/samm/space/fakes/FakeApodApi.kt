package com.samm.space.fakes

import com.samm.space.picture_of_the_day.domain.models.Apod

interface FakeApodApi {

    fun getData() = Apod(
        copyright = "Test",
        date = "2022-11-2023",
        explanation = "Test",
        hdurl = "https://images-assets.nasa.gov/image/as11-40-5874/as11-40-5874~thumb.jpg",
        media_type = "image",
        service_version = "Test",
        title = "Test",
        url = "https://images-assets.nasa.gov/image/as11-40-5874/as11-40-5874~thumb.jpg"
    )
}