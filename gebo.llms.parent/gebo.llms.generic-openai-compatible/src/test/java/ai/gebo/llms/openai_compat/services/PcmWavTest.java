/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.openai_compat.services;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Pins the WAV header put in front of the speech api's raw pcm: 24 kHz, 16 bit signed
 * little endian, one channel, the samples left as they are.
 */
class PcmWavTest {

	@Test
	void theSamplesGetAPlayableRiffWaveHeader() {
		byte[] pcm = new byte[] { 1, 2, 3, 4, 5, 6 };

		byte[] wav = PcmWav.wrap(pcm);

		ByteBuffer header = ByteBuffer.wrap(wav).order(ByteOrder.LITTLE_ENDIAN);
		assertEquals("RIFF", new String(wav, 0, 4, StandardCharsets.US_ASCII));
		assertEquals(36 + pcm.length, header.getInt(4));
		assertEquals("WAVE", new String(wav, 8, 4, StandardCharsets.US_ASCII));
		assertEquals("fmt ", new String(wav, 12, 4, StandardCharsets.US_ASCII));
		assertEquals(1, header.getShort(20), "PCM");
		assertEquals(1, header.getShort(22), "one channel");
		assertEquals(24000, header.getInt(24));
		assertEquals(48000, header.getInt(28), "bytes per second");
		assertEquals(2, header.getShort(32), "block align");
		assertEquals(16, header.getShort(34));
		assertEquals("data", new String(wav, 36, 4, StandardCharsets.US_ASCII));
		assertEquals(pcm.length, header.getInt(40));
		assertArrayEquals(pcm, Arrays.copyOfRange(wav, 44, wav.length));
	}
}
