package gov.hhs.cms.bluebutton.fhirstress.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import gov.hhs.cms.bluebutton.data.model.rif.RifFile;
import gov.hhs.cms.bluebutton.data.model.rif.RifFileType;

/**
 * This {@link RifFile} implementation represents a local file on disk.
 */
public final class LocalRifFile implements RifFile {
	private final Path localFile;
	private final RifFileType rifFileType;

	/**
	 * Constructs a new {@link LocalRifFile}.
	 * 
	 * @param localFile
	 *            the {@link Path} of the local file being represented
	 * @param rifFileType
	 *            the {@link RifFileType} of the file
	 */
	public LocalRifFile(Path localFile, RifFileType rifFileType) {
		this.localFile = localFile;
		this.rifFileType = rifFileType;
	}

	/**
	 * @see gov.hhs.cms.bluebutton.data.model.rif.RifFile#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return localFile.toAbsolutePath().toString();
	}

	/**
	 * @see gov.hhs.cms.bluebutton.data.model.rif.RifFile#getFileType()
	 */
	@Override
	public RifFileType getFileType() {
		return rifFileType;
	}

	/**
	 * @see gov.hhs.cms.bluebutton.data.model.rif.RifFile#getCharset()
	 */
	@Override
	public Charset getCharset() {
		return StandardCharsets.UTF_8;
	}

	/**
	 * @see gov.hhs.cms.bluebutton.data.model.rif.RifFile#open()
	 */
	@Override
	public InputStream open() {
		try {
			return new BufferedInputStream(new FileInputStream(localFile.toFile()));
		} catch (FileNotFoundException e) {
			throw new UncheckedIOException(e);
		}
	}
}
