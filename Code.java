import java.security.*;
import java.security.KeyStore.*;
import java.io.*;
import java.security.PublicKey;
import java.security.PrivateKey;
import javax.crypto.Cipher;
import com.google.common.io.BaseEncoding;
import java.nio.charset.*;
import sun.security.provider.*;
import  javax.crypto.*;
import java.security.cert.*;
import java.security.cert.Certificate;
import java.net.*;

//https://stackoverflow.com/questions/19937890/how-to-retrieve-my-public-and-private-key-from-the-keystore-we-created
// keytool -genkey -alias teiid -keyalg RSA -validity 365 -keystore server.keystore -storetype JKS
public class Code {

/**
 * @param args the command line arguments
 */
    public static void main(String[] args) {

        try {

            /* getting data for keystore */

            //File file = new File(System.getProperty("user.home") + File.separatorChar + ".keystore");
			File file = new File("D:\\Learnings\\JAva\\service-main\\server" + ".keystore");
            FileInputStream is = new FileInputStream(file);
            KeyStore keystore = loadKeyStore(file ,"changeit",KeyStore.getDefaultType());// KeyStore.getInstance(KeyStore.getDefaultType());
			String alias ="teiid";
			String password = "changeit";
			System.out.println(" Okay till here  " );
			
            final Key key = (PrivateKey) keystore.getKey(alias, password.toCharArray());

			final Certificate cert = keystore.getCertificate(alias);
			String msg = "ItccatI";
			encryptMessage( cert , "RSA" , msg);
			encryptMessage( cert , "AES" , msg); // not work
			encryptMessage( cert , "CBC" , msg); //not work 
			encryptMessage( cert , "PKCS5Padding" , msg);
			
			final PublicKey publicKey = cert.getPublicKey();
			System.out.println("Public Key " + publicKey.toString());	
			System.out.println("Private Key " + key);

       } catch (Exception e) {System.out.println(e);}

   }
   /*Encrypt the message in RSA /AES / etc 
   
   */ 
   public static void encryptMessage (Certificate cert , String encrypPattern , String message)  {
	
	try { 
	Cipher cipher = Cipher.getInstance(encrypPattern); //Cipher.getInstance("AES/CBC/PKCS5Padding");
	 cipher.init(Cipher.ENCRYPT_MODE,cert.getPublicKey());
	byte[] encryptedMessageInBytes = cipher.doFinal(message.getBytes("UTF-8"));
	String encryptedMsg = new String (encryptedMessageInBytes);
	System.out.println(" encryptedMsg "+message + " "+encryptedMsg );
	String base64EncodedEncryptedMsg = BaseEncoding.base64().encode(encryptedMessageInBytes);
	String base32EncodedEncryptedMsg = BaseEncoding.base32().encode(encryptedMessageInBytes);
	System.out.println(" base64EncodedEncryptedMsg "+message + " "+base64EncodedEncryptedMsg );
	System.out.println(" base32EncodedEncryptedMsg "+message + " "+base32EncodedEncryptedMsg );
	
	 } catch (Exception e) {System.out.println(e);}
   
   }
   
   /**
 * Reads a Java keystore from a file.
 * 
 * @param keystoreFile
 *          keystore file to read
 * @param password
 *          password for the keystore file
 * @param keyStoreType
 *          type of keystore, e.g., JKS or PKCS12
 * @return the keystore object
 * @throws KeyStoreException
 *           if the type of KeyStore could not be created
 * @throws IOException
 *           if the keystore could not be loaded
 * @throws NoSuchAlgorithmException
 *           if the algorithm used to check the integrity of the keystore
 *           cannot be found
 * @throws CertificateException
 *           if any of the certificates in the keystore could not be loaded
 */
public static KeyStore loadKeyStore(final File keystoreFile,
    final String password, final String keyStoreType)
    throws KeyStoreException, IOException, NoSuchAlgorithmException,
    CertificateException {
  if (null == keystoreFile) {
    throw new IllegalArgumentException("Keystore url may not be null");
  }
  System.out.println("Initializing key store: {}"+keystoreFile.getAbsolutePath());
  final URI keystoreUri = keystoreFile.toURI();
  final URL keystoreUrl = keystoreUri.toURL();
  final KeyStore keystore = KeyStore.getInstance(keyStoreType);
  InputStream is = null;
  try {
    is = keystoreUrl.openStream();
    keystore.load(is, null == password ? null : password.toCharArray());
   System.out.println("Loaded key store");
  } finally {
    if (null != is) {
     // is.close();
    }
  }
  return keystore;
}
   
   public static KeyPair getKeyPair(final KeyStore keystore, 
    final String alias, final String password) throws KeyStoreException , NoSuchAlgorithmException , UnrecoverableKeyException{
		
  final Key key = (PrivateKey) keystore.getKey(alias, password.toCharArray());

  final Certificate cert = keystore.getCertificate(alias);
  final PublicKey publicKey = cert.getPublicKey();

  return new KeyPair(publicKey, (PrivateKey) key);
}
   
   /* 
   
   public oldCode () { 
   
    // Information for certificate to be generated 
            String password = "changeit";
            String alias = "mykeys";
            String alias1 = "skeys";

            String filepath ="D:\\Learnings\\JAva\\service-main\\basic.txt";

             // getting the key
            keystore.load(is, password.toCharArray());
            PrivateKey key = (PrivateKey)keystore.getKey(alias, "bemylife".toCharArray());
            //PrivateKey key = cert1.getPrivateKey();
            //PublicKey key1= (PrivateKey)key;

            // Get certificate of public key 
            java.security.cert.Certificate cert = keystore.getCertificate(alias); 

            // Here it prints the public key
            System.out.println("Public Key:");
            System.out.println(cert.getPublicKey());

            // Here it prints the private key
            System.out.println("\nPrivate Key:");
            System.out.println(key);

            Cipher cipher = Cipher.getInstance("RSA");
			
            cipher.init(Cipher.ENCRYPT_MODE,cert.getPublicKey());

            String cleartextFile = "C:\\email.txt";
            String ciphertextFile = "D:\\ciphertextRSA.png";

            FileInputStream fis = new FileInputStream(cleartextFile);
            FileOutputStream fos = new FileOutputStream(ciphertextFile);
            CipherOutputStream cos = new CipherOutputStream(fos, cipher);

            byte[] block = new byte[32];
            int i;
            while ((i = fis.read(block)) != -1) {
                cos.write(block, 0, i);
            }
            cos.close();


            // computing the signature
            Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
            dsa.initSign(key);
            FileInputStream f = new FileInputStream(ciphertextFile);
            BufferedInputStream in = new BufferedInputStream(f);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) >= 0) {
               dsa.update(buffer, 0, len);
           };
           in.close();

           // Here it prints the signature
           System.out.println("Digital Signature :");
           System.out.println( dsa.sign());

           // Now Exporting Certificate 
           System.out.println("Exporting Certificate. ");
           byte[] buffer_out = cert.getEncoded();
           FileOutputStream os = new FileOutputStream(new File("d:\\signedcetificate.cer"));
           os.write(buffer_out);
           os.close();

           // writing signature to output.dat file 
           byte[] buffer_out1 = dsa.sign();
           FileOutputStream os1 = new FileOutputStream(new File("d:\\output.dat"));
           os1.write(buffer_out1);
           os1.close();
   } 
   */ 
   
   
   
}