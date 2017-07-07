package cs.b2b.mapping.e2e.util;

import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

public class Base64Util {

	public static void main(String[] args) {
		try {
			String str = "";
			String outFileName = "";
			
			str = "cGFja2FnZSBjcy5iMmIubWFwcGluZy5zY3JpcHRzDQoNCmltcG9ydCBncm9vdnkudXRpbC5YbWxTbHVycGVyDQppbXBvcnQgZ3Jvb3Z5LnhtbC5NYXJrdXBCdWlsZGVyDQppbXBvcnQgZ3Jvb3Z5LnhtbC5TdHJlYW1pbmdNYXJrdXBCdWlsZGVyDQppbXBvcnQgZ3Jvb3Z5LnhtbC5YbWxVdGlsDQoNCmltcG9ydCBqYXZhLnNxbC5Db25uZWN0aW9uDQppbXBvcnQgamF2YS5zcWwuUHJlcGFyZWRTdGF0ZW1lbnQNCmltcG9ydCBqYXZhLnNxbC5SZXN1bHRTZXQNCmltcG9ydCBqYXZhLmlvLlN0cmluZ1dyaXRlcg0KaW1wb3J0IGphdmEudGV4dC5TaW1wbGVEYXRlRm9ybWF0DQppbXBvcnQgamF2YS51dGlsLkRhdGUNCmltcG9ydCBqYXZhLnV0aWwuVGltZVpvbmUNCg0KLyoqDQogKiBJRwkJOiBJTlRUUkEgVkdNIFhNTA0KICogVmVyc2lvbgk6IDAuOA0KICovDQoNClN0cmluZyBtYXBwaW5nKFN0cmluZyBpbnB1dFhtbEJvZHksIFN0cmluZ1tdIHJ1bnRpbWVQYXJhbXMsIENvbm5lY3Rpb24gY29ubikgew0KCQ0KDQoJY3MuYjJiLmNvcmUubWFwcGluZy51dGlsLk1hcHBpbmdVdGlsIHV0aWwgPSBuZXcgY3MuYjJiLmNvcmUubWFwcGluZy51dGlsLk1hcHBpbmdVdGlsKCk7DQoNCgkvKioNCgkgKiBQYXJ0IEk6IHByZXAgaGFuZGxpbmcgaGVyZSwgcmVtb3ZlIFhNTCBCT00gZmxhZyBpbiBmaWxlIGJlZ2lubmluZywgY3VzdG9tZXIgc2FtcGxlIGNvbnRhaW5zIGl0DQoJICovDQoJaW5wdXRYbWxCb2R5ID0gdXRpbC5yZW1vdmVCT00oaW5wdXRYbWxCb2R5KQ0KCQ0KCS8qKg0KCSAqIFBhcnQgSUk6IGdldCBhcHAgbWFwcGluZyBydW50aW1lIHBhcmFtZXRlcnMNCgkgKi8NCglkZWYgYXBwU2Vzc2lvbklkID0gdXRpbC5nZXRSdW50aW1lUGFyYW1ldGVyKCJBcHBTZXNzaW9uSUQiLCBydW50aW1lUGFyYW1zKTsNCglkZWYgc291cmNlRmlsZU5hbWUgPSB1dGlsLmdldFJ1bnRpbWVQYXJhbWV0ZXIoIk9yaWdpbmFsU291cmNlRmlsZU5hbWUiLCBydW50aW1lUGFyYW1zKTsNCgkvL3BtdCBpbmZvDQoJZGVmIFRQX0lEID0gdXRpbC5nZXRSdW50aW1lUGFyYW1ldGVyKCJUUF9JRCIsIHJ1bnRpbWVQYXJhbXMpOw0KCWRlZiBNU0dfVFlQRV9JRCA9IHV0aWwuZ2V0UnVudGltZVBhcmFtZXRlcigiTVNHX1RZUEVfSUQiLCBydW50aW1lUGFyYW1zKTsNCglkZWYgRElSX0lEID0gdXRpbC5nZXRSdW50aW1lUGFyYW1ldGVyKCJESVJfSUQiLCBydW50aW1lUGFyYW1zKTsNCglkZWYgTVNHX1JFUV9JRCA9IHV0aWwuZ2V0UnVudGltZVBhcmFtZXRlcigiTVNHX1JFUV9JRCIsIHJ1bnRpbWVQYXJhbXMpOw0KDQoJLyoqDQoJICogUGFydCBJSUk6IHJlYWQgeG1sIGFuZCBwcmVwYXJlIG91dHB1dCB4bWwNCgkgKi8NCgkvL0ltcG9ydGFudDogdGhlIGlucHV0WG1sIGlzIHhtbCByb290IGVsZW1lbnQNCglkZWYgU3VibWl0VkdNID0gbmV3IFhtbFBhcnNlcigpLnBhcnNlVGV4dChpbnB1dFhtbEJvZHkpOw0KDQoJZGVmIHdyaXRlciA9IG5ldyBTdHJpbmdXcml0ZXIoKQ0KCWRlZiBvdXRYbWwgPSBuZXcgTWFya3VwQnVpbGRlcihuZXcgSW5kZW50UHJpbnRlcihuZXcgUHJpbnRXcml0ZXIod3JpdGVyKSwgIiIsIGZhbHNlKSk7IC8vbmV3IE1hcmt1cEJ1aWxkZXIod3JpdGVyKSAvL25ldyBJbmRlbnRQcmludGVyKG5ldyBQcmludFdyaXRlcih3cml0ZXIpLCAiIiwgZmFsc2UpIC0gbm8gZmluZSBwcmludA0KCW91dFhtbC5ta3AueG1sRGVjbGFyYXRpb24odmVyc2lvbjogIjEuMCIsIGVuY29kaW5nOiAidXRmLTgiKQ0KCQ0KDQoJLyoqDQoJICogUGFydCBJVjogbWFwcGluZyBzY3JpcHQgc3RhcnQgZnJvbSBoZXJlDQoJICovDQoNCglUaW1lWm9uZS5zZXREZWZhdWx0KFRpbWVab25lLmdldFRpbWVab25lKCdHTVQrOCcpKQ0KCWRlZiBjdXJyZW50U3lzdGVtRHQgPSBuZXcgRGF0ZSgpDQoNCglvdXRYbWwuJ25zMDpDUzJNYW5pZmVzdCcgKCd4bWxuczpuczAnOidodHRwOi8vd3d3LmNhcmdvc21hcnQuY29tL2VNYW5pZmVzdC9zY2hlbWFzL2VNYW5pZmVzdCcpDQoJew0KCQlkZWYgdmFyTWVzc2FnZUd1aWQgPSBTdWJtaXRWR00uTWVzc2FnZUd1aWQudGV4dCgpDQoJCVVVSUQgaWRPbmUgPSBVVUlELnJhbmRvbVVVSUQoKTsJCQ0KCQlkZWYgT3V0VVVJRCA9IE1TR19SRVFfSUQgKyAnLCcgKyBTdHJpbmcudmFsdWVPZihpZE9uZSkNCgkJZGVmIHZhckFjdGlvbiA9IFN1Ym1pdFZHTS5TdGF0ZS50ZXh0KCkNCgkJZGVmIHZhclNDQUMgPSB1dGlsLmdldENvbnZlcnNpb25CeUV4dENkZVdpdGhEZWZhdWx0KFRQX0lELCBNU0dfVFlQRV9JRCwgRElSX0lELCAnU0NBQycsIFN1Ym1pdFZHTS5DYXJyaWVyPy5QYXJ0eUlEPy5JRD8udGV4dCgpLFN1Ym1pdFZHTS5DYXJyaWVyPy5QYXJ0eUlEPy5JRD8udGV4dCgpLCBjb25uKQ0KCQlkZWYgdmFyU2VuZGVySUQgPSBUUF9JRAkJDQoJCWRlZiB2YXJSZWNlaXZlciA9IHV0aWwuZ2V0Q2FycmllclRwSWQodmFyU2VuZGVySUQsIE1TR19UWVBFX0lELCB2YXJTQ0FDLCBjb25uKQ0KCQkNCgkJDQoJCQ0KCQknbnMwOkhlYWRlcicgew0KCQkJJ25zMTpDb250cm9sTnVtYmVyJyAoJ3htbG5zOm5zMSc6J2h0dHA6Ly93d3cuY2FyZ29zbWFydC5jb20vY29tbW9uJywgdmFyTWVzc2FnZUd1aWQpDQoJCQknbnMxOk1zZ0RUJyAoJ3htbG5zOm5zMSc6J2h0dHA6Ly93d3cuY2FyZ29zbWFydC5jb20vY29tbW9uJykNCgkJCXsJCQkJDQoJCQkJJ25zMTpHTVQnIGN1cnJlbnRTeXN0ZW1EdC5mb3JtYXQoInl5eXktTU0tZGQnVCdISDptbTpzcy5Nc3oiKS5yZXBsYWNlRmlyc3QoJ0dNVCcsICcnKSANCgkJCX0gDQoJCQknbnMxOk1zZ0RpcmVjdGlvbicgKCd4bWxuczpuczEnOidodHRwOi8vd3d3LmNhcmdvc21hcnQuY29tL2NvbW1vbicsRElSX0lEKQ0KCQkJJ25zMTpNc2dUeXBlJyAoJ3htbG5zOm5zMSc6J2h0dHA6Ly93d3cuY2FyZ29zbWFydC5jb20vY29tbW9uJyxNU0dfVFlQRV9JRCkNCgkJCSduczE6U2VuZGVySUQnICgneG1sbnM6bnMxJzonaHR0cDovL3d3dy5jYXJnb3NtYXJ0LmNvbS9jb21tb24nLHZhclNlbmRlcklEKQ0KCQkJJ25zMTpSZWNlaXZlcklEJyAoJ3htbG5zOm5zMSc6J2h0dHA6Ly93d3cuY2FyZ29zbWFydC5jb20vY29tbW9uJywnQ0FSR09TTUFSVCcpDQoJCQknbnMxOkFjdGlvbicgKCd4bWxuczpuczEnOidodHRwOi8vd3d3LmNhcmdvc21hcnQuY29tL2NvbW1vbicsJ05FVycpCQ0KCQkJJ25zMTpJbnRlcmNoYW5nZU1lc3NhZ2VJRCcgKCd4bWxuczpuczEnOidodHRwOi8vd3d3LmNhcmdvc21hcnQuY29tL2NvbW1vbicsTVNHX1JFUV9JRCkNCgkJCSduczE6RGF0YVNvdXJjZScgKCd4bWxuczpuczEnOidodHRwOi8vd3d3LmNhcmdvc21hcnQuY29tL2NvbW1vbicsJ0IyQicpCQkNCgkJfQ0KCQkNCgkJLy8gQ1MyTWFuaWZlc3QuQm9keQ0KCQknbnMwOkJvZHknIHsNCgkJCS8vIENTMk1hbmlmZXN0LkJvZHkuVHJhbnNhY3Rpb25JbmZvcm1hdGlvbg0KCQkJJ25zMDpUcmFuc2FjdGlvbkluZm9ybWF0aW9uJw0KCQkJew0KCQkJCSduczE6TWVzc2FnZUlEJyAoJ3htbG5zOm5zMSc6J2h0dHA6Ly93d3cuY2FyZ29zbWFydC5jb20vY29tbW9uJyxPdXRVVUlEKQ0KCQkJCSduczE6R3JvdXBDb250cm9sTnVtYmVyJyAoJ3htbG5zOm5zMSc6J2h0dHA6Ly93d3cuY2FyZ29zbWFydC5jb20vY29tbW9uJyx2YXJNZXNzYWdlR3VpZCkNCgkJCQknbnMxOkludGVyY2hhbmdlVHJhbnNhY3Rpb25JRCcgICgneG1sbnM6bnMxJzonaHR0cDovL3d3dy5jYXJnb3NtYXJ0LmNvbS9jb21tb24nLHZhck1lc3NhZ2VHdWlkKQkJDQoJCQl9CQkJDQoJCQkvLyBDUzJNYW5pZmVzdC5Cb2R5Lk1hbmlmZXN0DQoJCQknbnMwOk1hbmlmZXN0Jw0KCQkJewkJCQkNCgkJCQlkZWYgdmFyQ29udGFpbmVyTnVtYmVyID0gU3VibWl0VkdNLkNvbnRhaW5lck51bWJlcj8udGV4dCgpDQoJCQkJZGVmIHZhckJpbGxPZkxhZGluZ051bWJlciA9IFN1Ym1pdFZHTS5CaWxsT2ZMYWRpbmdOdW1iZXI/LnRleHQoKQ0KCQkJCWRlZiB2YXJCb29raW5nTnVtYmVyID0gU3VibWl0VkdNLkJvb2tpbmdOdW1iZXI/LnRleHQoKQ0KCQkJCWRlZiB2YXJTdWJtaXR0ZXJSZWZlcmVuY2UgPSBTdWJtaXRWR00uU3VibWl0dGVyUmVmZXJlbmNlPy50ZXh0KCkNCg0KCQkJLy8JZGVmIHZhckZpbGVDcmVhdGlvbkRhdGV0aW1lID0gRGF0ZS5wYXJzZSgieXl5eS1NTS1kZCdUJ0hIOm1tOnNzLk1zeiIsIFN1Ym1pdFZHTS5NZXNzYWdlRGF0ZVRpbWUudGV4dCgpKS5mb3JtYXQoInl5eXlNTWRkSEhtbSIpDQoJCQkNCgkJCQlkZWYgZGF0ZSA9IG5ldyBTaW1wbGVEYXRlRm9ybWF0KCJ5eXl5LU1NLWRkJ1QnSEg6bW06c3MiKS5wYXJzZShTdWJtaXRWR00uTWVzc2FnZURhdGVUaW1lLnRleHQoKS5zdWJzdHJpbmcoMCwxOCkpDQoJCQkJZGVmIHZhckZpbGVDcmVhdGlvbkRhdGV0aW1lID0gbmV3IFNpbXBsZURhdGVGb3JtYXQoInl5eXlNTWRkSEhtbSIpLmZvcm1hdChkYXRlKQ0KLy8JCQkJMjAxNS0xMi0xN1QwOTozMDo0Ny4wWg0KCQkJCS8vIENTMk1hbmlmZXN0LkJvZHkuTWFuaWZlc3QuSGVhZGVyDQoJCQkJJ25zMDpIZWFkZXInIA0KCQkJCXsNCgkJCQkJLy8gQ1MyTWFuaWZlc3QuQm9keS5NYW5pZmVzdC5IZWFkZXIuT3JnYW5pemF0aW9uDQoJCQkJCSduczA6T3JnYW5pemF0aW9uJw0KCQkJCQl7DQoJCQkJCQlpZiAodmFyU0NBQyAhPSAiIikgew0KCQkJCQkJCSduczA6SWQnIHZhclNDQUMNCgkJCQkJCX0NCgkJCQkJCWVsc2Ugew0KCQkJCQkJCXRocm93IG5ldyBFeGNlcHRpb24oIk1pc3NpbmcgU0NBQyBDb2RlLiIpDQoJCQkJCQl9DQoJCQkJCQknbnMwOlN1cHBSZWYnDQoJCQkJCQl7DQoJCQkJCQkJJ25zMDpRdWFsaWZpZXInICdTRU5ERVInDQoJCQkJCQkJJ25zMDpSZWYnCQknU1BDJw0KCQkJCQkJfQ0KCQkJCQl9CQkJCQkNCgkJCQkJJ25zMDpUZXN0aW5nSW5kJyAnMCcNCgkJCQkJJ25zMDpFZGlNZXNzYWdlSUQnICdWR00nDQoJCQkJCSduczA6Q3VzdG9tc0lEJw0KCQkJCQl7DQoJCQkJCQknbnMwOlF1YWxpZmllcicgJ0NhcnJpZXInDQoJCQkJCQlpZiAodmFyU0NBQyAhPSAiIikgew0KCQkJCQkJCSduczA6VmFsdWUnIHZhclNDQUMNCgkJCQkJCX0NCgkJCQkJfQ0KCQkJCQkNCgkJCQkJJ25zMDpGaWxlQ3JlYXRpb25EYXRldGltZScNCgkJCQkJew0KCQkJCQkJJ25zMTpkYXRlU3RyJyAoJ3htbG5zOm5zMSc6J2h0dHA6Ly93d3cuY2FyZ29zbWFydC5jb20vZU1hbmlmZXN0L3NjaGVtYXMvZU1hbmlmZXN0L0RhdGFUeXBlcycsdmFyRmlsZUNyZWF0aW9uRGF0ZXRpbWUpDQoJCQkJCX0NCgkJCQkJJ25zMDpSZWZlcmVuY2VOdW0nDQoJCQkJCXsNCgkJCQkJCSduczA6UXVhbGlmaWVyJyAnTWVzc2FnZScNCgkJCQkJCWlmICh2YXJNZXNzYWdlR3VpZCAhPSAiIikgew0KCQkJCQkJCSduczA6VmFsdWUnIHZhck1lc3NhZ2VHdWlkDQoJCQkJCQl9DQoJCQkJCX0NCg0KCQkJCQlpZiAodmFyU3VibWl0dGVyUmVmZXJlbmNlICE9ICIiKSB7ICANCgkJCQkJCSduczA6UmVmZXJlbmNlTnVtJw0KCQkJCQkJew0KCQkJCQkJCSduczA6UXVhbGlmaWVyJyAnU0knDQoJCQkJCQkJJ25zMDpWYWx1ZScgdmFyU3VibWl0dGVyUmVmZXJlbmNlDQoJCQkJCQl9DQoJCQkJCX0NCgkJCQkJc3dpdGNoKHZhckFjdGlvbikgew0KCQkJCQkJY2FzZSAnT3JpZ2luYWwnIDogJ25zMDpNZXNzYWdlRnVuY3Rpb24nICc5JzsgYnJlYWs7DQoJCQkJCQljYXNlICdBbWVuZCcgOiAnbnMwOk1lc3NhZ2VGdW5jdGlvbicgJzUnOyBicmVhazsNCgkJCQkJCWNhc2UgJ0NhbmNlbCcgOiAnbnMwOk1lc3NhZ2VGdW5jdGlvbicgJzEnOyBicmVhazsNCgkJCQkJCWRlZmF1bHQgOiAnbnMwOk1lc3NhZ2VGdW5jdGlvbicgJzknOyBicmVhazsNCgkJCQkJfQkNCgkJCQkJaWYgKHZhclJlY2VpdmVyICE9ICIiKSB7DQoJCQkJCQknbnMwOlJlY2VpdmVyJyB2YXJSZWNlaXZlcg0KCQkJCQl9DQoNCgkJCQl9DQoJCQkJLy8gQ1MyTWFuaWZlc3QuQm9keS5NYW5pZmVzdC5CTEl0ZW1Db250YWluZXIJCQkJDQoJCQkJaWYgKHZhckJpbGxPZkxhZGluZ051bWJlciAhPSAiIikgew0KCQkJCQknbnMwOkJMSXRlbUNvbnRhaW5lcicNCgkJCQkJew0KCQkJCQkJJ25zMDpCTE51bScgdmFyQmlsbE9mTGFkaW5nTnVtYmVyDQoJCQkJCQknbnMwOkl0ZW1TZXFOdW0nICcxJw0KCQkJCQkJaWYgKHZhckNvbnRhaW5lck51bWJlciAhPSAiIikgew0KCQkJCQkJCSduczA6Q29udGFpbmVyTnVtJyB2YXJDb250YWluZXJOdW1iZXINCgkJCQkJCX0NCgkJCQkJfQkJCQkJCQ0KCQ0KCQkJCX0NCgkJCQkvLyBDUzJNYW5pZmVzdC5Cb2R5Lk1hbmlmZXN0LkJLDQoJCQkJJ25zMDpCSycNCgkJCQl7DQoJCQkJCWRlZiB2YXJTdWJtaXR0ZXJOYW1lID0gU3VibWl0VkdNLlN1Ym1pdHRlcj8uUGFydHlOYW1lMT8udGV4dCgpDQoJCQkJCWRlZiB2YXJSZXNwb25zaWJsZVBhcnR5TmFtZSA9IFN1Ym1pdFZHTS5SZXNwb25zaWJsZVBhcnR5Py5QYXJ0eU5hbWUxPy50ZXh0KCkNCgkJCQkJZGVmIHZhclZlcmlmaWNhdGlvbkRldGFpbHNOYW1lID0gU3VibWl0VkdNLlZlcmlmaWNhdGlvbkRldGFpbHM/LlBhcnR5TmFtZTE/LnRleHQoKQ0KCQkJCQlkZWYgdmFyVmVyaWZpY2F0aW9uRGVhdGlsc1NpZ25hdHVyZSA9IFN1Ym1pdFZHTS5WZXJpZmljYXRpb25EZXRhaWxzPy5WZXJpZmljYXRpb25TaWduYXR1cmU/LnRleHQoKQ0KCQkJCQlkZWYgdmFyV2VpZ2hpbmdQYXJ0eU5hbWUgPSBTdWJtaXRWR00uV2VpZ2hpbmdQYXJ0eT8uUGFydHlOYW1lMT8udGV4dCgpDQoJCQkJCWRlZiB2YXJBdXRob3JpemVkUGFydHlOYW1lID0gU3VibWl0VkdNLkF1dGhvcml6ZWRQYXJ0eT8uUGFydHlOYW1lMT8udGV4dCgpDQoJCQkJCWRlZiB2YXJDQ0MgPSB1dGlsLmdldEVESUNkZVJlZihUUF9JRCwgJ01hcENDQycsIERJUl9JRCwgJ1ZFUk1BUycsICdEZWZhdWx0Q0NDJywgdmFyU0NBQywgJ0RFRkFVTFQnLGNvbm4pDQoJCQkJCS8vIE1hcCBvZiBTdWJtaXR0ZXIgUGFydHkJCQkJDQoJCQkJCWlmICghU3VibWl0VkdNLlN1Ym1pdHRlci5pc0VtcHR5KCkpIHsNCgkJCQkJCSduczA6UGFydHknDQoJCQkJCQl7DQoJCQkJCQkJJ25zMDpUeXBlJyAnU1BDJw0KCQkJCQkJCSduczA6U2VxTnVtJyAnMScNCgkJCQkJCQlpZiAodmFyU3VibWl0dGVyTmFtZSAhPSAiIikgew0KCQkJCQkJCQknbnMwOk5hbWUnIHZhclN1Ym1pdHRlck5hbWUNCgkJCQkJCQl9DQoJCQkJCQkJJ25zMDpTdXBwUGFydHlJRCcNCgkJCQkJCQl7DQoJCQkJCQkJCSduczA6UXVhbGlmaWVyJyAnUEFSVFlfTEVWRUwnDQoJCQkJCQkJCSduczA6UGFydHlJRCcgJ1NITVQnDQoJCQkJCQkJfQ0KCQkJCQkJfQ0KCQkJCQl9DQoJCQkJCS8vIE1hcCBvZiBSZXNwb25zaWJsZSBQYXJ0eQ0KCQkJCQlpZiAoIVN1Ym1pdFZHTS5SZXNwb25zaWJsZVBhcnR5LmlzRW1wdHkoKSkgew0KCQkJCQkJJ25zMDpQYXJ0eScNCgkJCQkJCXsNCgkJCQkJCQknbnMwOlR5cGUnICdTUEMnDQoJCQkJCQkJJ25zMDpTZXFOdW0nICcxJw0KCQkJCQkJCWlmICh2YXJSZXNwb25zaWJsZVBhcnR5TmFtZSAhPSAiIikgew0KCQkJCQkJCQknbnMwOk5hbWUnIHZhclJlc3BvbnNpYmxlUGFydHlOYW1lDQoJCQkJCQkJfQkJCQkJCQkNCgkJCQkJCQknbnMwOlN1cHBQYXJ0eUlEJw0KCQkJCQkJCXsNCgkJCQkJCQkJJ25zMDpRdWFsaWZpZXInICdQQVJUWV9MRVZFTCcNCgkJCQkJCQkJJ25zMDpQYXJ0eUlEJyAnQ05UUicNCgkJCQkJCQl9DQoJCQkJCQl9DQoJCQkJCX0NCgkJCQkJLy8gTWFwIG9mIFZlcmlmaWNhdGlvbiBQYXJ0eSANCgkJCQkJaWYgKCFTdWJtaXRWR00uVmVyaWZpY2F0aW9uRGV0YWlscy5pc0VtcHR5KCkpIHsNCgkJCQkJCSduczA6UGFydHknDQoJCQkJCQl7DQoJCQkJCQkJJ25zMDpUeXBlJyAnQU0nDQoJCQkJCQkJJ25zMDpTZXFOdW0nICcxJw0KCQkJCQkJCWlmIChTdWJtaXRWR00uVmVyaWZpY2F0aW9uRGV0YWlscz8uRGVsZWdhdGVkPy50ZXh0KCkgPT0gImZhbHNlIiAmJiB2YXJSZXNwb25zaWJsZVBhcnR5TmFtZSAhPSAiIikgew0KCQkJCQkJCQknbnMwOk5hbWUnIHZhclJlc3BvbnNpYmxlUGFydHlOYW1lDQoJCQkJCQkJfSBlbHNlIGlmIChTdWJtaXRWR00uVmVyaWZpY2F0aW9uRGV0YWlscz8uRGVsZWdhdGVkPy50ZXh0KCkgPT0gInRydWUiICYmIHZhckF1dGhvcml6ZWRQYXJ0eU5hbWUgIT0gIiIpIHsNCgkJCQkJCQkJJ25zMDpOYW1lJyB2YXJBdXRob3JpemVkUGFydHlOYW1lDQoJCQkJCQkJfQ0KCQkJCQkJCWlmICh2YXJWZXJpZmljYXRpb25EZWF0aWxzU2lnbmF0dXJlICE9ICIiKSB7DQoJCQkJCQkJCSduczA6Q29udGFjdFBlcnNvbicNCgkJCQkJCQkJew0KCQkJCQkJCQkJJ25zMDpGaXJzdE5hbWUnIHZhclZlcmlmaWNhdGlvbkRlYXRpbHNTaWduYXR1cmUNCgkJCQkJCQkJfQkNCgkJCQkJCQl9DQoJCQkJCQkJJ25zMDpTdXBwUGFydHlJRCcNCgkJCQkJCQl7DQoJCQkJCQkJCSduczA6UXVhbGlmaWVyJyAnUEFSVFlfTEVWRUwnDQoJCQkJCQkJCSduczA6UGFydHlJRCcgJ0NOVFInDQoJCQkJCQkJfQ0KCQkJCQkJfQ0KCQkJCQl9DQoJCQkJCS8vIE1hcCBvZiBXZWlnaHRpbmcgUGFydHkNCgkJCQkJaWYgKCFTdWJtaXRWR00uV2VpZ2hpbmdQYXJ0eS5pc0VtcHR5KCkpIHsNCgkJCQkJCSduczA6UGFydHknDQoJCQkJCQl7DQoJCQkJCQkJJ25zMDpUeXBlJyAnV1BBJw0KCQkJCQkJCSduczA6U2VxTnVtJyAnMScNCgkJCQkJCQlpZiAodmFyV2VpZ2hpbmdQYXJ0eU5hbWUgIT0gIiIpIHsNCgkJCQkJCQkJJ25zMDpOYW1lJyB2YXJXZWlnaGluZ1BhcnR5TmFtZQ0KCQkJCQkJCX0NCgkJCQkJCQknbnMwOlN1cHBQYXJ0eUlEJw0KCQkJCQkJCXsNCgkJCQkJCQkJJ25zMDpRdWFsaWZpZXInICdQQVJUWV9MRVZFTCcNCgkJCQkJCQkJJ25zMDpQYXJ0eUlEJyAnQ05UUicNCgkJCQkJCQl9DQoJCQkJCQl9DQoJCQkJCX0NCgkJCQkJLy8gTWFwIG9mIFBhcnR5IHR5cGUgVEIgd2l0aCBEZWZhdWx0IENDQw0KCQkJCQlpZiAodmFyQ0NDICE9ICIiKSB7DQoJCQkJCQknbnMwOlBhcnR5Jw0KCQkJCQkJew0KCQkJCQkJCSduczA6VHlwZScgJ1RCJw0KCQkJCQkJCSduczA6U2VxTnVtJyAnMScNCgkJCQkJCQknbnMwOlN1cHBQYXJ0eUlEJw0KCQkJCQkJCXsNCgkJCQkJCQkJJ25zMDpRdWFsaWZpZXInICdXUEFfSU5ERVgnDQoJCQkJCQkJCSduczA6UGFydHlJRCcgJzAnDQoJCQkJCQkJfQ0KCQkJCQkJCSduczA6U3VwcFBhcnR5SUQnDQoJCQkJCQkJew0KCQkJCQkJCQknbnMwOlF1YWxpZmllcicgJ1BBUlRZX0xFVkVMJw0KCQkJCQkJCQknbnMwOlBhcnR5SUQnICdTSE1UJw0KCQkJCQkJCX0NCgkJCQkJCQknbnMwOlN1cHBQYXJ0eUlEJw0KCQkJCQkJCXsNCgkJCQkJCQkJJ25zMDpRdWFsaWZpZXInICdDQ0MnDQoJCQkJCQkJCSduczA6UGFydHlJRCcgdmFyQ0NDDQoJCQkJCQkJfQ0KCQkJCQkJfQ0KCQkJCQl9DQoJCQkJCSduczA6UGFydHknDQoJCQkJCXsNCgkJCQkJCSduczA6VHlwZScgJ0VESScNCgkJCQkJCSduczA6U2VxTnVtJyAnMScNCgkJCQkJCWlmIChUUF9JRC5zdWJzdHJpbmcoMCwgVFBfSUQuaW5kZXhPZigiX1ZHTSIpKS50cmltKCkgIT0gIiIpIHsNCgkJCQkJCQknbnMwOk5hbWUnIFRQX0lELnN1YnN0cmluZygwLCBUUF9JRC5pbmRleE9mKCJfVkdNIikpLnRyaW0oKQ0KCQkJCQkJfQ0KLyoJCQkJCQknbnMwOlN1cHBQYXJ0eUlEJw0KCQkJCQkJew0KCQkJCQkJCSduczA6UXVhbGlmaWVyJyAnUEFSVFlfTEVWRUwnDQoJCQkJCQkJJ25zMDpQYXJ0eUlEJyAnQ05UUicNCgkJCQkJCX0qLw0KCQkJCQl9DQoJCQkJCWlmICh2YXJTQ0FDICE9ICIiKSB7DQoJCQkJCQknbnMwOlNjYWMnIHZhclNDQUMNCgkJCQkJfQ0KCQkJCQlpZiAodmFyQm9va2luZ051bWJlciAhPSAiIikgew0KCQkJCQkJJ25zMDpCS051bScgdmFyQm9va2luZ051bWJlcg0KCQkJCQl9DQoJCQkJfQ0KCQkJCS8vIENTMk1hbmlmZXN0LkJvZHkuTWFuaWZlc3QuQktJdGVtQ29udGFpbmVyDQoJCQkJaWYgKHZhckJvb2tpbmdOdW1iZXIgIT0gIiIpIHsNCgkJCQkJJ25zMDpCS0l0ZW1Db250YWluZXInDQoJCQkJCXsNCgkJCQkJCSduczA6QktOdW0nIHZhckJvb2tpbmdOdW1iZXINCgkJCQkJCSduczA6SXRlbVNlcU51bScgJzEnDQoJCQkJCQlpZiAodmFyQ29udGFpbmVyTnVtYmVyICE9ICIiKSB7DQoJCQkJCQkJJ25zMDpDb250YWluZXJOdW0nIHZhckNvbnRhaW5lck51bWJlcg0KCQkJCQkJfQ0KCQkJCQl9DQoJCQkJfQ0KCQkJCS8vIENTMk1hbmlmZXN0LkJvZHkuTWFuaWZlc3QuQ29udGFpbmVyDQoJCQkJJ25zMDpDb250YWluZXInDQoJCQkJew0KCQkJCQlpZiAodmFyQ29udGFpbmVyTnVtYmVyICE9ICIiKSB7DQoJCQkJCQknbnMwOkNvbnRhaW5lck51bScgdmFyQ29udGFpbmVyTnVtYmVyDQoJCQkJCX0JCQkJCQ0KCQkJCQlkZWYgdmFyVmVyaWZpZWRHcm9zc01hc3MgPSBTdWJtaXRWR00uVmVyaWZpZWRHcm9zc01hc3MuTWFzcy50ZXh0KCkNCgkJCQkJaWYgKHZhclZlcmlmaWVkR3Jvc3NNYXNzICE9ICIiKSB7DQoJCQkJCQknbnMwOldlaWdodCcNCgkJCQkJCXsNCgkJCQkJCQknbnMwOlF1YWxpZmllcicgJ0dST1NTJw0KCQkJCQkJCSduczA6VmFsdWUnICgndW5pdCc6dXRpbC5nZXRDb252ZXJzaW9uQnlFeHRDZGUoVFBfSUQsIE1TR19UWVBFX0lELCBESVJfSUQsICdXZWlnaHRRdWFsaWZpZXInLCBTdWJtaXRWR00uVmVyaWZpZWRHcm9zc01hc3MuVU9NLnRleHQoKSwgY29ubiksIHZhclZlcmlmaWVkR3Jvc3NNYXNzKQ0KCQkJCQkJfQ0KCQkJCQl9DQoJCQkJCWRlZiB2YXJJU09Db250YWluZXJUeXBlID0gU3VibWl0VkdNLkNvbnRhaW5lckRldGFpbHMuSVNPQ29udGFpbmVyVHlwZS50ZXh0KCkNCgkJCQkJaWYgKHZhcklTT0NvbnRhaW5lclR5cGUgIT0gIiIpIHsNCgkJCQkJCSduczA6U2l6ZVR5cGUnDQoJCQkJCQl7DQoJCQkJCQkJJ25zMDpRdWFsaWZpZXInICdJU08nDQoJCQkJCQkJJ25zMDpWYWx1ZScgdmFySVNPQ29udGFpbmVyVHlwZQ0KCQkJCQkJfQ0KCQkJCQl9DQoJCQkJCWlmICh2YXJTdWJtaXR0ZXJSZWZlcmVuY2UgIT0gIiIpIHsNCgkJCQkJCSduczA6U3VwcFJlZicNCgkJCQkJCXsNCgkJCQkJCQknbnMwOlF1YWxpZmllcicgJ1NJJw0KCQkJCQkJCSduczA6UmVmJyB2YXJTdWJtaXR0ZXJSZWZlcmVuY2UNCgkJCQkJCX0NCgkJCQkJfQ0KCQkJCQkvL3V0aWwuZ2V0Q29udmVyc2lvbldpdGhEZWZhdWx0KFRQX0lELCBNU0dfVFlQRV9JRCwgRElSX0lELCAnQWdlR3JvdXBCRUcwMS1PTExYTUxNc2dTdGF0dXMnLCBjdXJyZW50RGV0YWlsLkJFRy5CRUcwMS50ZXh0KCksICdORVcnLCBjb25uKQ0KCQkJCQkNCgkJCQkJZGVmIHZhclZHTURldGVybWluYXRpb25NZXRob2QgPSBTdWJtaXRWR00uVkdNRGV0ZXJtaW5hdGlvbk1ldGhvZC50ZXh0KCkNCgkJCQkJZGVmIHZhckNvbnZlcnRNZXRob2QgPSB1dGlsLmdldENvbnZlcnNpb25CeUV4dENkZShUUF9JRCwgTVNHX1RZUEVfSUQsIERJUl9JRCwgJ1dlaWdodE1ldGhvZCcsIHZhclZHTURldGVybWluYXRpb25NZXRob2QsIGNvbm4pDQoJCQkJCWlmICh2YXJWR01EZXRlcm1pbmF0aW9uTWV0aG9kICE9ICIiKSB7DQoJCQkJCQknbnMwOk1pc2NJbmZvJw0KCQkJCQkJew0KCQkJCQkJCSduczA6UXVhbGlmaWVyJyAnV0VJR0hUX01FVEhPRCcNCgkJCQkJCQlpZiAodmFyQ29udmVydE1ldGhvZCAhPSAiIikgew0KCQkJCQkJCQknbnMwOlZhbHVlJyB2YXJDb252ZXJ0TWV0aG9kDQoJCQkJCQkJfQ0KCQkJCQkJfQ0KCQkJCQl9DQoJCQkJCS8vIEZvciBWR01YTUwgQWNrIGhhbmRsaW5nIGJ5IGRpZmZlcmVudCBjYXJyaWVycwkJCQ0KCQkJCQlkZWYgdmFyU2NhY0FjayA9IHV0aWwuZ2V0Q29udmVyc2lvbldpdGhTY2FjQnlFeHRDZGUobnVsbCwgTVNHX1RZUEVfSUQsIERJUl9JRCwgJ1ZFUk1BU19BY2tfSGFuZGxlJywgJ0NvbnRhaW5lck51bWJlcicsIHZhclNDQUMsIGNvbm4pDQoJCQkJCQ0KCQkJCQlpZiAodmFyU2NhY0FjayAhPSAiIikgew0KCQkJCQkJJ25zMDpNaXNjSW5mbycNCgkJCQkJCXsNCgkJCQkJCQknbnMwOlF1YWxpZmllcicgJ01zZ0lkJw0KCQkJCQkJCSduczA6VmFsdWUnIE91dFVVSUQgKyB2YXJDb250YWluZXJOdW1iZXINCgkJCQkJCX0JCQkJDQoJCQkJCX0NCgkJCQkJJ25zMDpNaXNjSW5mbycNCgkJCQkJew0KCQkJCQkJJ25zMDpRdWFsaWZpZXInICdXUEFfSU5ERVgnDQoJCQkJCQknbnMwOlZhbHVlJyAxDQoJCQkJCX0NCg0KCQkJCQlkZWYgdmFyVkdNRGV0ZXJtaW5hdGlvbkRhdGVUaW1lID0gU3VibWl0VkdNLlZHTURldGVybWluYXRpb25EYXRlVGltZS50ZXh0KCkNCgkJCQkJDQoJCQkJCWlmICh2YXJWR01EZXRlcm1pbmF0aW9uRGF0ZVRpbWUgIT0gIiIpIHsNCgkJCQkJCSduczA6V2VpZ2h0RGF0ZScNCgkJCQkJCXsNCgkJCQkJCQknbnMxOkdNVCcgICgneG1sbnM6bnMxJzonaHR0cDovL3d3dy5jYXJnb3NtYXJ0LmNvbS9jb21tb24nLHZhclZHTURldGVybWluYXRpb25EYXRlVGltZSkNCgkJCQkJCX0JDQoJCQkJCX0NCgkJCQl9DQoJCQl9CQkJDQoJCX0NCgkJDQoJCWRlZiBzZXRNb25SZXN1bHQgPSB1dGlsLlNldE1vbkVESUNvbnRyb2xObyh2YXJTZW5kZXJJRCwgdmFyUmVjZWl2ZXIsIE1TR19UWVBFX0lELCAnWE1MJywgdmFyTWVzc2FnZUd1aWQsIHZhck1lc3NhZ2VHdWlkLCB2YXJNZXNzYWdlR3VpZCwgT3V0VVVJRCwgTVNHX1JFUV9JRCwgY29ubikNCgkJZGVmIGluc2VydFNDQUMgPSB1dGlsLkluc2VydFNDQUMoTVNHX1JFUV9JRCwgRElSX0lELCAnT1NDQUMnLCB2YXJTQ0FDLCBjb25uKQ0KCQkvL2RlZiB0bXBBY2tLZXlUeXBlID0gdXRpbC5nZXRDb252ZXJzaW9uQ29tbW9uV2l0aFNjYWMoTVNHX1RZUEVfSUQsIERJUl9JRCwgJ1ZFUk1BU19BY2tfSGFuZGxlJywgdmFyU0NBQyxjb25uKQ0KCQkvL2RlZiBDaGVja0Fja0tleVR5cGUgPSB0bXBBY2tLZXlUeXBlLnN1YnN0cmluZygwLCB0bXBBY2tLZXlUeXBlLmluZGV4T2YoIjsiKSkudHJpbSgpDQoJCQ0KCQkvL2lmIChDaGVja0Fja0tleVR5cGUgPT0gIkNvbnRhaW5lck51bWJlciIpew0KCQkJDQoJCS8vfQ0KCX0NCglyZXR1cm4gd3JpdGVyLnRvU3RyaW5nKCk7DQp9";
			outFileName = "c:/11/YusenLogistics_VGMXML_to_CS2_Manifest_map_Jason.groovy";
			
			byte[] bs = getBytesFromBASE64(str);
			
			LocalFileUtil.writeBytesToFile(outFileName, bs);
			
			System.out.println("done.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getBASE64(byte[] bs) {
		if (bs == null)
			return null;
		return (new sun.misc.BASE64Encoder()).encode(bs);
	}
	
	public static String getBASE64(String s) {
		if (s == null)
			return null;
		return (new sun.misc.BASE64Encoder()).encode(s.getBytes());
	}

	public static String getFromBASE64(String s) throws IOException {
		if (s == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		
		byte[] b = decoder.decodeBuffer(s);
		return new String(b);
	}
	
	public static String getFromBASE64WithEncoding(String s, String encoding) throws IOException {
		if (s == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		
		byte[] b = decoder.decodeBuffer(s);
		if (encoding!=null && encoding.trim().length()>0)
			return new String(b, encoding);
		else
			return new String(b);
	}
	
	public static InputStream getInputStreamFromBASE64(String s) throws IOException {
		if (s == null)
			return null;
		
		BASE64Decoder decoder = new BASE64Decoder();
		return new ByteArrayInputStream(decoder.decodeBuffer(s));
		
	}
	
	public static byte[] getBytesFromBASE64(String s) throws IOException {
		if (s == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		
		byte[] b = decoder.decodeBuffer(s);
		return b;
		
	}
	
	public static String getZipBodyFromBase64(String s, String encoding) throws IOException {
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] content = decoder.decodeBuffer(s);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ByteArrayInputStream bais = new ByteArrayInputStream(content);
		ZipInputStream zis = new ZipInputStream(bais);
		zis.getNextEntry();
        byte[] b = new byte[1024];
        for (int c = zis.read(b, 0, 1024); c != -1; c = zis.read(b, 0, 1024)) {
        	baos.write(b, 0, c);                    
        }
		String unzippedContent = new String(baos.toByteArray(), encoding);
		return unzippedContent;
	}
	
//	public static String getFromBASE64(String s) throws Exception {
//		if (s == null)
//			return null;
//		BASE64Decoder decoder = new BASE64Decoder();
//		try {
//			byte[] b = decoder.decodeBuffer(s);
//			return new String(b);
//		} catch (Exception e) {
//			throw e;
//		}
//	}
}