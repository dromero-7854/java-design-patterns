package facade.example;

import java.io.File;

import facade.example.facade.VideoConversionFacade;

public class Demo {

	public static void main(String[] args) {
		VideoConversionFacade converter = new VideoConversionFacade();
		File mp4Video = converter.convertVideo("youtubevideo.ogg", "mp4");
		// ...
	}

}
