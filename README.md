
# Dakiya
## Introduction
Introducing **Dakiya**, an Android library designed to render beautiful and highly customizable notifications for both push and local notifications. With its easy-to-use API, **Dakiya** allows developers to create visually appealing notifications with rich media, custom layouts and interactive actions. Whether you're sending important updates, reminders, or promotional content, **Dakiya** helps you show notifications that not only capture users' attention but also enhance their overall experience.

## Provide the gradle dependency

- Add the below dependency in your app.gradle
```kotlin
    dependencies {
        implementation 'com.github.Riyaz002:Dakiya:0.3.2-alpha'
    }
```
- Make sure you have include the required repository.  Add this into your project.gradle
```kotlin
    dependencyResolutionManagement {
		repositories {
            ...
			maven { url 'https://jitpack.io' }
            ...
		}
    }
```
Sync your project once done.

## Usage
### Setup
In your manifest file. Set the notification small icon.
```xml
	<meta-data 
		android:name="com.riyaz.dakiya.Notification_Small_Icon"
		android:value="@drawable/your_notification_small_icon"
	/>
```

### Show notification
```kotlin
    val message = Message(  
        id = 1,  
        title = "Hi Daak",  
		subtitle = "A notification is a brief alert or message sent to users to inform them of updates, reminders, or important events." +  
	           " and helps users stay informed about relevant activities or changes without needing to constantly check the application or service manually.",  
		image = "https://images.unsplash.com/photo-1725881282478-54c8f001e0a0?q=80&w=1858&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",  
		style = Style.DEFAULT,
		channel = CHANNEL_ID
	)
	
    Dakiya.showNotification(message)	
```

### With Firebase notification service
**Dakiya** works with firebase push notifications. So, it expects to recieve data values in RemoteMessage.

**Sever** side example for push notification for ***Dakiya***

```js
	```
// The topic name can be optionally prefixed with "/topics/".
    const topic = 'highScores';
    const message = {
      data: {
        title: 'Hi Daak',
        subtitle: 'A notification is a brief alert or message sent to users to inform them of updates, reminders, or important events' +  
            " It can appear on various devices, such as smartphones or computers," +  
            " and helps users stay informed about relevant activities or changes without needing to constantly check the application or service manually.",
		image = 'https://images.unsplash.com/photo-1725881282478-54c8f001e0a0?q=80&w=1858&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
		style = "BIG_TIMER",
		endTime = "2024-09-08T12:34:56.000Z" //required for BIG_TIMER style
      },
      topic: topic
    };

// Send a message to devices subscribed to the provided topic.
    getMessaging().send(message)
      .then((response) => {
    // Response is a message ID string.
       console.log('Successfully sent message:', response);
    })
   .catch((error) => {
     console.log('Error sending message:', error);
   });
```
> Or [Send message usign API in firebase](https://firebase.google.com/docs/cloud-messaging/migrate-v1)

Inside your FirebaseMessagingService class
```kotlin
    class MyNotificationService : FirebaseMessagingService() {  
        override fun onMessageReceived(message: RemoteMessage) {  
            super.onMessageReceived(message)
            if(Dakiya.isDakiyaNotification(message)){
		        val dakiyaMessage = message.toDakiyaMessage(notificationId = 10)
		        Dakiya.showNotification(dakiyaMessage)
            }
        }
    }
```

Dakiya is in development phase and has no production release yet. Please use this only in non-production app.
