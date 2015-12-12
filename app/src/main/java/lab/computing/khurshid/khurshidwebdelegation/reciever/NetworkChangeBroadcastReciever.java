package lab.computing.khurshid.khurshidwebdelegation.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

import lab.computing.khurshid.khurshidwebdelegation.dto.WebRequest;
import lab.computing.khurshid.khurshidwebdelegation.management.WebDelegateManager;

public class NetworkChangeBroadcastReciever extends BroadcastReceiver {

	WebDelegateManager webDelegateManager;
	Context context;

	@Override
	public void onReceive(Context context, Intent intent) {

		this.context = context;

		final ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		final android.net.NetworkInfo wifi = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		final android.net.NetworkInfo mobile = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		if (wifi.isAvailable() || mobile.isAvailable()) {
			Log.d("GUFRAN", "Yepiiiiiiiiii   Network Available");

			Toast.makeText(context, "Gufran Wifi Available ", Toast.LENGTH_LONG)
					.show();
			// ---------------->>
			// get the data

			webDelegateManager = new WebDelegateManager(context);

			List<WebRequest> webRequests = webDelegateManager
					.getPendingWebRequests();

			for (WebRequest webRequest : webRequests) {
				// call the loopj task for every Web Request--->>
				accessWebService(webRequest);
			}

			// ---------------->>>

		} else {
			Toast.makeText(context, "Wifi Gone ", Toast.LENGTH_LONG).show();
		}

	}

	public void accessWebService(final WebRequest webRequest) {

//		AsyncHttpClient client = new AsyncHttpClient();
//		RequestParams params = new RequestParams();
//
//		WebParam<String, String> webParam = webRequest.getWebParams();
//
//		for (String key : webParam.keySet()) {
//			params.add(key, webParam.get(key));
//		}
//
//		// params.add("email", emailID);
//		// params.add("password", password);
//
//		client.post(webRequest.getUrl(), params,
//				new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						if (arg2 != null) {
//							String resultString = new String(arg2);
//							// WebRequest wr = webDelegateManager
//							// .getWebRequest(webRequest.getId());
//							webRequest.setResponseString(resultString);
//							webRequest.setRequestStatus(RequestStatus.SUCCESS);
//							webRequest.setResponseTimeStamp(new Date());
//							webDelegateManager.updateWebRequest(webRequest);
//
//							new ExportDatabaseFileTask().execute("");
//
//						}
//					}
//
//					@Override
//					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//							Throwable arg3) {
//						if (arg2 != null) {
//							String resultString = new String(arg2);
//
//							// WebRequest wr = webDelegateManager
//							// .getWebRequest(webRequest.getId());
//
//							webRequest.setResponseString(resultString);
//							webRequest.setRequestStatus(RequestStatus.ERROR);
//							webRequest.setResponseTimeStamp(new Date());
//							webDelegateManager.updateWebRequest(webRequest);
//
//							new ExportDatabaseFileTask().execute("");
//						}
//					}
//
//				});

	}

	private class ExportDatabaseFileTask extends
			AsyncTask<String, Void, Boolean> {
		// private final ProgressDialog dialog = new ProgressDialog(g);

		// can use UI thread here
		protected void onPreExecute() {
			// this.dialog.setMessage("Exporting database...");
			// this.dialog.show();
		}

		// automatically done on worker thread (separate from UI thread)
		protected Boolean doInBackground(final String... args) {

			File dbFile = new File(
					Environment.getDataDirectory()
							+ "/data/com.appxperts.appxpertswebdelegate/databases/AppXpertsWebDataBase.db");

			File exportDir = new File(
					Environment.getExternalStorageDirectory(), "");
			if (!exportDir.exists()) {
				exportDir.mkdirs();
			}
			File file = new File(exportDir, dbFile.getName());

			try {
				file.createNewFile();
				this.copyFile(dbFile, file);
				return true;
			} catch (IOException e) {
				Log.e("mypck", e.getMessage(), e);
				return false;
			}
		}

		// can use UI thread here
		protected void onPostExecute(final Boolean success) {
			// if (this.dialog.isShowing()) {
			// this.dialog.dismiss();
			// }
			if (success) {
				Toast.makeText(context, "Export successful!",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(context, "Export failed", Toast.LENGTH_SHORT)
						.show();
			}
		}

		void copyFile(File src, File dst) throws IOException {
			FileChannel inChannel = new FileInputStream(src).getChannel();
			FileChannel outChannel = new FileOutputStream(dst).getChannel();
			try {
				inChannel.transferTo(0, inChannel.size(), outChannel);
			} finally {
				if (inChannel != null)
					inChannel.close();
				if (outChannel != null)
					outChannel.close();
			}
		}

	}

}
