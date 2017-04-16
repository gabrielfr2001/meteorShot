package br.com.roska.util;

import java.net.URL;

import br.com.roska.main.Painter;

public class ImageLoader<T> {

	private T obj;

	public ImageLoader(T t) {
		this.obj = t;
	}

	public URL getPath(String subPath) {
		try {
			return obj.getClass().getResource(subPath);
		} catch (Exception e) {
			e.printStackTrace();
			Painter.logger.log(e);
			return null;
		}
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}
}
