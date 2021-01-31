package org.bonal.birthdaycard.model

private const val LOCAL_VIDEO_URL = "asset:///test.mp4"
private const val REMOTE_VIDEO_URL =
    "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
private const val REMOTE_IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/76/Boris_Johnson_official_portrait_%28cropped%29.jpg/440px-Boris_Johnson_official_portrait_%28cropped%29.jpg"

data class BirthdayData(
    val birthdayHost: BirthdayHost,
    val guestList: List<BirthdayGuest>,
    val birthdayCardMessage: String
)

val defaultBirthdayData = BirthdayData(
    birthdayHost = BirthdayHost(name = "Birthday boy", pictureUrl = REMOTE_IMAGE_URL),
    guestList = listOf(
        BirthdayGuest(
            name = "Guest A",
            pictureUrl = "asset:///olivier.jpg",
            message = "Joyeux anniversaire mon ami!",
            video = "REMOTE_VIDEO_URL"
        ),
        BirthdayGuest(
            name = "Guest B",
            pictureUrl = "asset:///olivier.jpg",
            message = "I wish you a wonderful birthday and I hope you will enjoy my present...",
            video = LOCAL_VIDEO_URL
        ),
        BirthdayGuest(
            name = "Guest B",
            pictureUrl = REMOTE_IMAGE_URL
        ),
        BirthdayGuest(
            name = "Guest C",
            video = REMOTE_VIDEO_URL
        ),
        BirthdayGuest(
            "Guest D",
            video = LOCAL_VIDEO_URL
        ),
        BirthdayGuest("Guest E"),
        BirthdayGuest(
            "Guest F",
            video = LOCAL_VIDEO_URL
        ),
    ),
    birthdayCardMessage = "We thought we would celebrate your birthday by sending you some nice messages. Check what your friends have to say…"
)