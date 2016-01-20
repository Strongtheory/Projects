package deo.gl;

public class Font {
	public final Texture texture;
	public final int gWidth; //glyphWidth
	public final int gHeight; //glyphHeight
	public final TextureRegions[] glyphs = new TextureRegions[96];
	
	public Font(Texture texture, int offsetX, int offsetY,
				int gPerRow, int gWidth, int gHeight){
		this.texture = texture;
		this.gWidth = gWidth;
		this.gHeight = gHeight;
		int x = offsetX;
		int y = offsetY;
		for (int i = 0; i < 96; i++) {
			glyphs[i] = new TextureRegions(texture, x, y, gWidth, gHeight);
			x += gWidth;
			if(x == offsetX + gPerRow * gWidth){
				x = offsetX;
				y += gHeight;
			}
		}
	}
	
	public void drawText(Sprite batcher, String text, float x, float y){
		for (int i = 0; i < text.length(); i++) {
			int c = text.charAt(i) - ' ';
			if(c < 0 || c > glyphs.length - 1)
				continue;
			TextureRegions glyph = glyphs[c];
			batcher.drawSprite(x, y, gWidth, gHeight, glyph);
			x += gWidth;
		}
	}
}
