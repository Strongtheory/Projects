package game.opengl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import game.framework.GLGame;

public class ObjectLoader {
	
	public static Vertices3 load(GLGame game, String filename){
		InputStream inputStream = null;
		try {
			inputStream = game.getFileIO().readAsset(filename);
			List<String> lines = readLines(inputStream);
			float[] vertices = new float[lines.size() * 3];
			float[] normals = new float[lines.size() * 3];
			float[] uv = new float[lines.size() * 2];
			int numVertices = 0;
			int numNormals = 0;
			int numUV = 0;
			int numFaces = 0;
			
			int[] faceVertices = new int[lines.size() * 3];
			int[] faceNormals = new int[lines.size() * 3];
            int[] faceUV = new int[lines.size() * 3];
            int vertexIndex = 0;
            int normalIndex = 0;
            int uvIndex = 0;
            int faceIndex = 0;
            
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);

                if (line.startsWith("v ")) {
                    String[] tokens = line.split("[ ]+");
                    vertices[vertexIndex] = Float.parseFloat(tokens[1]);
                    vertices[vertexIndex + 1] = Float.parseFloat(tokens[2]);
                    vertices[vertexIndex + 2] = Float.parseFloat(tokens[3]);
                    vertexIndex += 3;
                    numVertices++;
                    continue;
                }

                if (line.startsWith("vn ")) {
                    String[] tokens = line.split("[ ]+");
                    normals[normalIndex] = Float.parseFloat(tokens[1]);
                    normals[normalIndex + 1] = Float.parseFloat(tokens[2]);
                    normals[normalIndex + 2] = Float.parseFloat(tokens[3]);
                    normalIndex += 3;
                    numNormals++;
                    continue;
                }

                if (line.startsWith("vt")) {
                    String[] tokens = line.split("[ ]+");
                    uv[uvIndex] = Float.parseFloat(tokens[1]);
                    uv[uvIndex + 1] = Float.parseFloat(tokens[2]);
                    uvIndex += 2;
                    numUV++;
                    continue;
                }

                if (line.startsWith("f ")) {
                    String[] tokens = line.split("[ ]+");

                    String[] parts = tokens[1].split("/");
                    faceVertices[faceIndex] = getIndex(parts[0], numVertices);
                    if (parts.length > 2)
                        faceNormals[faceIndex] = getIndex(parts[2], numNormals);
                    if (parts.length > 1)
                        faceUV[faceIndex] = getIndex(parts[1], numUV);
                    faceIndex++;

                    parts = tokens[2].split("/");
                    faceVertices[faceIndex] = getIndex(parts[0], numVertices);
                    if (parts.length > 2)
                        faceNormals[faceIndex] = getIndex(parts[2], numNormals);
                    if (parts.length > 1)
                        faceUV[faceIndex] = getIndex(parts[1], numUV);
                    faceIndex++;

                    parts = tokens[3].split("/");
                    faceVertices[faceIndex] = getIndex(parts[0], numVertices);
                    if (parts.length > 2)
                        faceNormals[faceIndex] = getIndex(parts[2], numNormals);
                    if (parts.length > 1)
                        faceUV[faceIndex] = getIndex(parts[1], numUV);
                    faceIndex++;
                    numFaces++;
                    continue;
                }
                
                if(line.startsWith("f ")){
                	String[] tokens = line.split("[ ]+");
                	String[] parts = tokens[1].split("/");
                	faceVertices[faceIndex] = getIndex(parts[0], numVertices);
                	if (parts.length > 2)
                        faceNormals[faceIndex] = getIndex(parts[2], numNormals);
                    if (parts.length > 1)
                        faceUV[faceIndex] = getIndex(parts[1], numUV);
                    faceIndex++;

                    parts = tokens[2].split("/");
                    faceVertices[faceIndex] = getIndex(parts[0], numVertices);
                    if (parts.length > 2)
                        faceNormals[faceIndex] = getIndex(parts[2], numNormals);
                    if (parts.length > 1)
                        faceUV[faceIndex] = getIndex(parts[1], numUV);
                    faceIndex++;

                    parts = tokens[3].split("/");
                    faceVertices[faceIndex] = getIndex(parts[0], numVertices);
                    if (parts.length > 2)
                        faceNormals[faceIndex] = getIndex(parts[2], numNormals);
                    if (parts.length > 1)
                        faceUV[faceIndex] = getIndex(parts[1], numUV);
                    faceIndex++;
                    numFaces++;
                    continue;
                }
            }
            float[] verts = new float[(numFaces * 3)
                                      * (3 + (numNormals > 0 ? 3 : 0) + (numUV > 0 ? 2 : 0))];
            for (int i = 0, vi = 0; i < numFaces * 3; i++) {
                int vertexPosition = faceVertices[i] * 3;
                verts[vi++] = vertices[vertexPosition];
                verts[vi++] = vertices[vertexPosition + 1];
                verts[vi++] = vertices[vertexPosition + 2];

                if (numUV > 0) {
                    int uvIdx = faceUV[i] * 2;
                    verts[vi++] = uv[uvIdx];
                    verts[vi++] = 1 - uv[uvIdx + 1];
                }

                if (numNormals > 0) {
                    int normalPosition = faceNormals[i] * 3;
                    verts[vi++] = normals[normalPosition];
                    verts[vi++] = normals[normalPosition + 1];
                    verts[vi++] = normals[normalPosition + 2];
                }
            }

            Vertices3 model = new Vertices3(game.getGlGraphics(), numFaces * 3,
                    0, false, numUV > 0, numNormals > 0);
            model.setVertices(verts, 0, verts.length);
            return model;
		} catch (Exception e) {
			throw new RuntimeException("Load Failed: " + filename + "'", e);
		} finally {
			if (inputStream != null){
				try {
					inputStream.close();
				} catch (Exception e2) {
				}
			}
		}
	}
	
	static int getIndex(String index, int size){
		int position = Integer.parseInt(index);
		if(position < 0)
			return size + position;
		else
			return position - 1;
	}
	
	static List<String> readLines(InputStream inputStream) throws IOException {
		List<String> lines = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new 
				InputStreamReader(inputStream));
		String line = null;
		while((line = reader.readLine()) != null)
			lines.add(line);
		return lines;
	}
}
