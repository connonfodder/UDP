package com.aadhk.kds.util;

public class DataTransfer {

/*	// 写入数据
	public static void writeData(Socket socket, Object obj) throws IOException {
		Gson gson = new Gson();
		String json = gson.toJson(obj);
		OutputStream stream = socket.getOutputStream();
		stream.write((json + "\r\n").getBytes(("UTF-8")));
	}

	//读取客户端数据
	public static String readData(Socket socket) throws IOException {
		InputStream inputstream = socket.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(inputstream));
		String content = null;
		while ((content = br.readLine()) != null) {
			return content;
		}
		return content;
	}*/

}
