package ar.com.hjg.pngj;

import java.io.OutputStream;

import ar.com.hjg.pngj.chunks.ChunkHelper;
import ar.com.hjg.pngj.chunks.ChunkRaw;

/**
 * Outputs the stream for IDAT chunk , fragmented at fixed size (64k default).
 */
class PngIDatChunkOutputStream extends ProgressiveOutputStream {
	private static final int SIZE_DEFAULT = 65556; // 64k rather arbitrary
	private final OutputStream outputStream;
	// for some special IDAT (fDAT) that prepend some bytes to each chunk (this
	// is not include in the size)
	private byte[] prefix = null;

	PngIDatChunkOutputStream(OutputStream outputStream) {
		this(outputStream, 0);
	}

	PngIDatChunkOutputStream(OutputStream outputStream, int size) {
		super(size > 0 ? size : SIZE_DEFAULT);
		this.outputStream = outputStream;
	}

	@Override
	protected final void flushBuffer(byte[] b, int len) {
		int len2 = prefix == null ? len : len + prefix.length;
		ChunkRaw c = new ChunkRaw(len2, ChunkHelper.b_IDAT, false);
		if(len ==len2)
			c.data = b;
		else {
			
		}
		c.writeChunk(outputStream);
	}

	void setPrefix(byte[] pref) {
		if (pref == null)
			prefix = null;
		else {
			this.prefix = new byte[pref.length];
			System.arraycopy(pref, 0, prefix, 0, pref.length);
		}
	}
}
