# BirthdayCard
Virtual Birthday Card allowing playing videos from all your friends &amp; more...

## How to make it work
The data that the app will display will be retrieve from a json file name "birthday_card_info.json" that must be hosted somewhere on a server.
The base url for the file must be set to the `birthday_card_info_base_url` property in gradle.properties (either the project one or your own ~/.gradle/gradle.properties) or passed as a -P property to the gradle command line.
Here's an example of a gradle.properties content:
```properties
birthday_card_info_base_url = "http://mydomain.com/my-birthday-card-folder/"
```

The file itself must have the following format
```json
{
  "birthdayCardMessage": "For your birthday, we've decided to send you some nice messages and gather them in this app. Check what your friends have to say…",
  "birthdayCardBackground": "http://whatever.com/background.jpg",
  "galleryButtonLabel": "Go to media gallery",
  "birthdayHost": {
     "name": "Jérôme",
     "pictureUrl": "https://mydomain.com/my-birthday-card-folder/bob.jpg"
  },
  "guestList": [
    {
      "name": "Maria",
      "message": "Happy birthday!",
      "phoneNumber":"+44 12345678"
    },
    {
      "name": "Olivier",
      "message": "Joyeux anniversaire!",
      "pictureUrl": "https://mydomain.com/my-birthday-card-folder/olivier.jpg",
      "video": "https://mydomain.com/my-birthday-card-folder/olivier.mp4",
      "phoneNumber":"+33 12345678"
    },
    {
      "name": "Esteban",
      "message": "Feliz cumpleaños!!!",
      "pictureUrl": "https://mydomain.com/my-birthday-card-folder/esteban.jpg",
      "video": "https://mydomain.com/my-birthday-card-folder/esteban.mp4",
    },
    {
      "name": "Nicolas",
      "pictureUrl": "https://mydomain.com/my-birthday-card-folder/nico.jpg",
      "phoneNumber":"+33 111111111"
    }

  ]
}
```

When `galleryButtonLabel` is not null in this first json, a button will appear on the messages page header showing media content fetched from a second json file, placed in the place on the same server and named "birthday_memories.json":

```json
{
	"galleryViewTitle": "Good Times Gallery",
	"memories": [{
			"title": "Sample Text Content",
			"description": "Acting as Section Header..."
		},
		{
			"title": "Sample Image Content",
			"description": "blablablab",
			"pictureUrl": "http://aplaceontheinternet.com/images/whatever.jpg",
			"videoUrl": null
		},
		{
			"title": "Sample Video Content",
			"description": "blablablab",
			"pictureUrl": null,
			"videoUrl": "http://aplaceontheinternet.com/images/whatever.jpg"
		}
	]
}
```
