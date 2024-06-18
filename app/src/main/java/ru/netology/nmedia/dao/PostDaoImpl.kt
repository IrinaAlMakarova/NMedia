package ru.netology.nmedia.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import ru.netology.nmedia.dto.Post

class PostDaoImpl(private val db: SQLiteDatabase) : PostDao {
    companion object {
        val DDL = """
        CREATE TABLE ${PostColumns.TABLE} (
            ${PostColumns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${PostColumns.COLUMN_AUTHOR} TEXT NOT NULL,
            ${PostColumns.COLUMN_CONTENT} TEXT NOT NULL,
            ${PostColumns.COLUMN_PUBLISHED} TEXT NOT NULL,
            ${PostColumns.COLUMN_LIKES} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_LIKED_BY_ME} BOOLEAN NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_SHARE} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_SHARE_BY_ME} BOOLEAN NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_VISIBILITY} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_VISIBILITY_BY_ME} BOOLEAN NOT NULL DEFAULT 0
        );
        """.trimIndent()
    }

    object PostColumns {
        const val TABLE = "posts"
        const val COLUMN_ID = "id"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_PUBLISHED = "published"
        const val COLUMN_LIKES = "likes"
        const val COLUMN_LIKED_BY_ME = "likedByMe"
        const val COLUMN_SHARE = "share"
        const val COLUMN_SHARE_BY_ME = "shareByMe"
        const val COLUMN_VISIBILITY = "visibility"
        const val COLUMN_VISIBILITY_BY_ME = "visibilityByMe"
        val ALL_COLUMNS = arrayOf(
            COLUMN_ID,
            COLUMN_AUTHOR,
            COLUMN_CONTENT,
            COLUMN_PUBLISHED,
            COLUMN_LIKES,
            COLUMN_LIKED_BY_ME,
            COLUMN_SHARE,
            COLUMN_SHARE_BY_ME,
            COLUMN_VISIBILITY,
            COLUMN_VISIBILITY_BY_ME

        )
    }

    override fun getAll(): List<Post> {
        val posts = mutableListOf<Post>()
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            null,
            null,
            null,
            null,
            "${PostColumns.COLUMN_ID} DESC"
        ).use {
            while (it.moveToNext()) {
                posts.add(map(it))
            }
        }
        return posts
    }

    override fun save(post: Post): Post {
        val values = ContentValues().apply {
            if (post.id != 0L) {
                put(PostColumns.COLUMN_CONTENT, post.id)
            }
            // TODO: remove hardcoded values
            put(PostColumns.COLUMN_AUTHOR, "Me")
            put(PostColumns.COLUMN_CONTENT, post.content)
            put(PostColumns.COLUMN_PUBLISHED, "now")
        }
        val id = db.replace(PostColumns.TABLE, null, values)
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null,
        ).use {
            it.moveToNext()
            return map(it)
        }
    }

    override fun likeById(id: Long) {
        db.execSQL(
            """
           UPDATE posts SET
               ${PostColumns.COLUMN_LIKES} = ${PostColumns.COLUMN_LIKES} + CASE WHEN ${PostColumns.COLUMN_LIKED_BY_ME} THEN -1 ELSE 1 END,
               ${PostColumns.COLUMN_LIKED_BY_ME} = CASE WHEN ${PostColumns.COLUMN_LIKED_BY_ME} THEN 0 ELSE 1 END
           WHERE id = ?;
        """.trimIndent(), arrayOf(id)
        )
    }

    override fun shareById(id: Long) {
        db.execSQL(
            """
           UPDATE posts SET
               ${PostColumns.COLUMN_SHARE} = ${PostColumns.COLUMN_SHARE} + 1,
               ${PostColumns.COLUMN_SHARE_BY_ME} = 1 
           WHERE id = ?;
        """.trimIndent(), arrayOf(id)
        )
    }

    override fun visibilityById(id: Long) {
        db.execSQL(
            """
           UPDATE posts SET
               ${PostColumns.COLUMN_VISIBILITY} = ${PostColumns.COLUMN_VISIBILITY} + 1,
               ${PostColumns.COLUMN_VISIBILITY_BY_ME} = 1 
           WHERE id = ?;
        """.trimIndent(), arrayOf(id)
        )
    }

    override fun removeById(id: Long) {
        db.delete(
            PostColumns.TABLE,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }

    private fun map(cursor: Cursor): Post {
        with(cursor) {
            return Post(
                id = getLong(getColumnIndexOrThrow(PostColumns.COLUMN_ID)),
                author = getString(getColumnIndexOrThrow(PostColumns.COLUMN_AUTHOR)),
                content = getString(getColumnIndexOrThrow(PostColumns.COLUMN_CONTENT)),
                published = getString(getColumnIndexOrThrow(PostColumns.COLUMN_PUBLISHED)),
                likes = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKES)),
                likedByMe = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKED_BY_ME)) != 0,
                share = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_SHARE)),
                shareByMe = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_SHARE_BY_ME)) != 0,
                visibility = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_VISIBILITY)),
                visibilityByMe = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_VISIBILITY_BY_ME)) != 0,
            )
        }
    }
}
