package coil.fetch

import android.content.Context
import android.graphics.drawable.Drawable
import coil.bitmappool.BitmapPool
import coil.decode.DataSource
import coil.decode.DrawableDecoderService
import coil.decode.Options
import coil.size.Size
import coil.util.isVector
import coil.util.toDrawable

internal class DrawableFetcher(
    private val context: Context,
    private val drawableDecoder: DrawableDecoderService
) : Fetcher<Drawable> {

    override fun key(data: Drawable): String? = null

    override suspend fun fetch(
        pool: BitmapPool,
        data: Drawable,
        size: Size,
        options: Options
    ): FetchResult {
        val isVector = data.isVector
        return DrawableResult(
            drawable = if (isVector) {
                drawableDecoder.convert(
                    drawable = data,
                    config = options.config,
                    size = size,
                    scale = options.scale,
                    allowInexactSize = options.allowInexactSize
                ).toDrawable(context)
            } else {
                data
            },
            isSampled = isVector,
            dataSource = DataSource.MEMORY
        )
    }
}
