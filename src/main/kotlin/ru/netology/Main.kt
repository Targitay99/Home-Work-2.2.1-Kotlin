package ru.netology

fun main() {

    var post1 = WallService.add(Post(1, 2, 3, 4, 5, "Раз", 7, 8, true, null, Likes(10)))
    var post2 = WallService.add(Post(2, 2, 3, 4, 5, "Два", 7, 8, true, Comment(9), Likes(10)))
    var post3 = WallService.add(Post(3, 2, 3, 4, 5, "Три", 7, 8, true, null, Likes(10)))
    var post4 = Post(3, 2, 3, 4, 5, "пост не с этой страницы", 7, 8, true, Comment(), Likes())

    post1.attachment += AudioAttachment("audio", Audio())
    post1.attachment += AudioAttachment("audio", Audio(1, albumId = 2, artist = "Pety", title = "Song"))
    post1.attachment += VideoAttachment("video", Video())
    post1.attachment += PhotoAttachment("photo", Photo())

    WallService.printAttachment(post1)



    WallService.printPost()

    post1 = post1.copy(text = "Четыре")
    WallService.update(post1)

    post2 = post2.copy(ownerId = 500, text = "Пять")
    WallService.update(post2)

    post3 = post3.copy(text = "Шесть", data = 10, comment = Comment(9))
    WallService.update(post3)

    post4 = post4.copy(text = "Семь", data = 10) // Этот пост не выведется
    WallService.update(post4)

    WallService.printPost()

}

data class Post(
    val id: Int = 0,
    val ownerId: Int = 0,
    val fromId: Int = 0,
    val createdBy: Int = 0,
    val data: Int = 0,
    val text: String = " ",
    val replyOwnerId: Int = 0,
    val replyPostId: Int = 0,
    val friendsOnly: Boolean = false,
    val comment: Comment?,               // поле Nullable по задаче 1
    val likes: Likes,
    var attachment: Array<Attachment> = emptyArray()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Post

        if (!attachment.contentEquals(other.attachment)) return false

        return true
    }

    override fun hashCode(): Int {
        return attachment.contentHashCode()
    }
}


data class Comment(
    val count: Int = 0,
    val canPost: Boolean = true,
    val groupsCanPost: Boolean = true,
    val canClose: Boolean = true,
    val canOpen: Boolean = true
)

data class Likes(
    val count: Int = 0,
    val userLikes: Boolean = false,
    val canLike: Boolean = true,
    val canPublish: Boolean = true
)

object WallService {
    private var posts = emptyArray<Post>()

    fun add(post: Post): Post {
        val postId = posts.hashCode()
        posts += post.copy(id = postId)
        return posts.last()
    }

    fun update(post: Post): Boolean {
        var canUpdate = false
        for ((i, updatedPost) in posts.withIndex())
            if (post.id == updatedPost.id) {
                posts[i] = post.copy(ownerId = updatedPost.ownerId, data = updatedPost.data)
                canUpdate = true
            }
        return canUpdate
    }

    fun printPost() {
        for (i in 1..posts.size) {
            println("${posts[i - 1].id}   ${posts[i - 1].text}    ${posts[i - 1].data} ${posts[i - 1].comment} ")
        }
    }

    fun printAttachment(post: Post) {
        for (i in 1..post.attachment.size)
            println("${post.id} ${post.attachment[i - 1]} ")
    }
}


