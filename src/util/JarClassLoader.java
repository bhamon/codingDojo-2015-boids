package util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class JarClassLoader extends ClassLoader implements Closeable {

	private static final int BUFFER_SIZE = 512;

	private static final String CLASS_EXT = ".class";

	private final JarFile m_file;

	public JarClassLoader(File p_path) throws IOException {
		if (p_path == null) {
			throw new IllegalArgumentException("Path can't be null");
		}

		m_file = new JarFile(p_path);
	}

	@Override
	protected Class<?> findClass(String p_name) throws ClassNotFoundException {
		if (p_name == null) {
			throw new IllegalArgumentException("Name can't be null");
		}

		ZipEntry entry = m_file.getEntry(p_name + CLASS_EXT);
		if (entry == null) {
			throw new ClassNotFoundException();
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			int read;
			byte[] buffer = new byte[BUFFER_SIZE];
			InputStream in = m_file.getInputStream(entry);

			while ((read = in.read(buffer)) != -1) {
				out.write(buffer, 0, read);
			}
		} catch (IOException ex) {
			throw new ClassNotFoundException();
		}

		return defineClass(p_name, out.toByteArray(), 0, out.size());
	}

	@Override
	public void close() throws IOException {
		m_file.close();
	}
}
