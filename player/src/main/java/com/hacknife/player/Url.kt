package com.hacknife.player

import android.icu.text.CaseMap
import android.os.Bundle

/**
 * author  : hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : IPlayer
 */
class Url {
    companion object {
        private const val DEFAULT_KEY = "DEFAULT_KEY"
        fun fromBundle(bundle: Bundle): Url {
            val url = Url()
            url.headers = bundle.getSerializable("HEADER") as HashMap<String, String>
            url.title = bundle.getString("TITLE")
            url.thumbnail = bundle.getString("THUMBNAIL")
            url.dataSource = bundle.getSerializable("DATA_SOURCE") as HashMap<String, Any>?
            url.currentKey = bundle.getString("CURRENT_KEY")!!
            url.isLoop = bundle.getBoolean("IS_LOOP")!!
            return url
        }
    }

    private var headers: HashMap<String, String>? = null
    private var title: String? = null
    private var thumbnail: String? = null
    private var dataSource: HashMap<String, Any>? = null
    private var currentKey: String? = "DEFAULT_KEY"
    private var isLoop: Boolean? = false

    class Builder {
        private val url = Url()
            .apply {
                headers = hashMapOf()
                dataSource = hashMapOf()
            }

        fun header(key: String, value: String): Builder {
            url.headers!![key] = value
            return this
        }

        fun title(title: String): Builder {
            url.title = title
            return this
        }

        fun isLoop(isLoop: Boolean): Builder {
            url.isLoop = isLoop
            return this
        }

        fun thumbnail(thumbnail: String): Builder {
            url.thumbnail = thumbnail
            return this
        }

        fun dataSource(source: Any): Builder {
            url.dataSource!![DEFAULT_KEY] = source
            return this
        }

        fun dataSource(key: String, source: Any): Builder {
            url.dataSource!![key] = source
            return this
        }

        fun build() = url
    }

    fun getHeader() = headers ?: hashMapOf()
    fun getTitle() = title ?: ""
    fun getThumbnail() = thumbnail ?: ""
    fun getCurrentUrl() = dataSource!![currentKey]
    fun getCurrentKey() = currentKey
    fun isLoop() = isLoop!!

    fun toBundle(): Bundle {
        val bundle = Bundle()
        bundle.putSerializable("HEADER", getHeader())
        bundle.putString("TITLE", title)
        bundle.putString("THUMBNAIL", thumbnail)
        bundle.putSerializable("DATA_SOURCE", dataSource)
        bundle.putSerializable("CURRENT_KEY", currentKey)
        bundle.putBoolean("IS_LOOP", isLoop()!!)
        return bundle
    }
}