/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.openai_compat.services;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

/**
 * Raw PCM samples made playable: the speech api's {@code pcm} answer is "the raw samples
 * in 24kHz (16-bit signed, low-endian), without the header" (OpenAI's text to speech
 * guide), which no audio player opens. A WAV header in front of the same samples makes
 * it a file the browser plays as it plays the mp3 the other voices answer with.
 */
final class PcmWav {
	/** The sample rate of the speech api's pcm format. */
	static final int SAMPLE_RATE = 24000;
	/** Its sample size, signed little endian. */
	static final int BITS_PER_SAMPLE = 16;
	/** One channel. */
	static final int CHANNELS = 1;
	private static final int HEADER_SIZE = 44;

	private PcmWav() {
	}

	/** The samples behind a 44 byte RIFF/WAVE header describing them. */
	static byte[] wrap(byte[] pcm) {
		final int dataSize = pcm != null ? pcm.length : 0;
		final int blockAlign = CHANNELS * BITS_PER_SAMPLE / 8;
		final ByteBuffer wav = ByteBuffer.allocate(HEADER_SIZE + dataSize).order(ByteOrder.LITTLE_ENDIAN);
		wav.put("RIFF".getBytes(StandardCharsets.US_ASCII));
		wav.putInt(36 + dataSize);
		wav.put("WAVE".getBytes(StandardCharsets.US_ASCII));
		wav.put("fmt ".getBytes(StandardCharsets.US_ASCII));
		wav.putInt(16); // the PCM fmt chunk size
		wav.putShort((short) 1); // PCM
		wav.putShort((short) CHANNELS);
		wav.putInt(SAMPLE_RATE);
		wav.putInt(SAMPLE_RATE * blockAlign); // bytes per second
		wav.putShort((short) blockAlign);
		wav.putShort((short) BITS_PER_SAMPLE);
		wav.put("data".getBytes(StandardCharsets.US_ASCII));
		wav.putInt(dataSize);
		if (dataSize > 0) {
			wav.put(pcm);
		}
		return wav.array();
	}
}
