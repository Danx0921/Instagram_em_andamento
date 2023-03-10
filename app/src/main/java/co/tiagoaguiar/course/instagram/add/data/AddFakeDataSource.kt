package co.tiagoaguiar.course.instagram.add.data

import android.net.Uri
import android.os.Handler
import android.os.Looper
import co.tiagoaguiar.course.instagram.common.base.RequestCallback
import co.tiagoaguiar.course.instagram.common.model.Database
import co.tiagoaguiar.course.instagram.common.model.Post
import java.util.UUID

class AddFakeDataSource : AddDataSource {

    override fun createPost(
        userUUID: String,
        uri: Uri,
        caption: String,
        callback: RequestCallback<Boolean>
    ) {
        Handler(Looper.getMainLooper()).postDelayed({
            var posts = Database.posts[userUUID]

            if (posts == null) {
                posts = mutableSetOf()
                Database.posts[userUUID] = posts

            }

            val post = Post(
                UUID.randomUUID().toString(),
                uri,
                caption,
                System.currentTimeMillis(),
                Database.sessionAuth!!
            )

            posts.add(post)

            var followers   = Database.followers[userUUID]

            if(followers ==null){
                followers = mutableSetOf()
                Database.followers[userUUID] = followers
            } else {
                for (follower in followers)
                    Database.feeds[follower]?.add(post)
            }
        callback.onSuccess(true)
        }, 2000)
    }
}