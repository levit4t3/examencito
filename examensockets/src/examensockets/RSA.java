package examensockets;

/**
 *
 * @author ALVARO'S
 */
import java.math.BigInteger;
import java.util.*;
import java.io.*;


public class RSA{

    //variables
    int tamPrimo;
    BigInteger n, q, p;
    BigInteger totient;
    BigInteger e, d;

    public int getTamPrimo() {
        return tamPrimo;
    }

    public void setTamPrimo(int tamPrimo) {
        this.tamPrimo = tamPrimo;
    }

    public BigInteger getN() {
        return n;
    }

    public void setN(BigInteger n) {
        this.n = n;
    }

    public BigInteger getQ() {
        return q;
    }

    public void setQ(BigInteger q) {
        this.q = q;
    }

    public BigInteger getP() {
        return p;
    }

    public void setP(BigInteger p) {
        this.p = p;
    }

    public BigInteger getTotient() {
        return totient;
    }

    public void setTotient(BigInteger totient) {
        this.totient = totient;
    }

    public BigInteger getE() {
        return e;
    }

    public void setE(BigInteger e) {
        this.e = e;
    }

    public BigInteger getD() {
        return d;
    }

    public void setD(BigInteger d) {
        this.d = d;
    }
    
    
    
    //constructor
    public RSA(int tamPrimo){
        this.tamPrimo = tamPrimo;
    }

    //metodo para generar numeros primos

    public void generarPrimos(){
        //para los primos son p y q
        p = new BigInteger(tamPrimo, 10, new Random());
        do q = new BigInteger(tamPrimo, 10, new Random());
            while(q.compareTo(p)==0);
    }

    //generar las claves

    public void generarClaves(){
        // n = p*q
        n = p.multiply(q); //p*q
        //p(hi) = (p-1)*(q-1)
        totient = p.subtract(BigInteger.valueOf(1));
        totient = totient.multiply(q.subtract(BigInteger.valueOf(1)));

        //elegir el numero coprimo o primo relativo menor que n

        do e = new BigInteger(2*tamPrimo, new Random());
            while ((e.compareTo(totient)!=-1) || 
            (e.gcd(totient).compareTo(BigInteger.valueOf(1))!=0));
        //ahora debemos hacer la operacion modulo
        // d = e^ 1 mod totient

        d = e.modInverse(totient);

    }

    /*
    Cifrar con el numero e ya que "e" es la clave publica
    */ 

    public BigInteger[] encriptar(String mensaje){
        //variables
        int i;
        byte[] temp = new byte[1];
        byte[] digitos = mensaje.getBytes();
        BigInteger[] bigdigitos = new BigInteger[digitos.length];

        //lo primero que debemos hacer es correr el tamaño de bigdigitos
        for(i = 0; i<bigdigitos.length; i++){
            temp[0] = digitos[i];
            bigdigitos[i] = new BigInteger(temp);
        }

        //vamos a cifrar
        BigInteger[] encriptado = new BigInteger[bigdigitos.length];

        for(i = 0; i<bigdigitos.length; i++){
            encriptado[i] = bigdigitos[i].modPow(e,n);
        }
        return encriptado;
    }

    /*
    descifrar array de biginteger
    */ 

    public String desencriptar(BigInteger[] encriptado){
        BigInteger[] desencriptar = new BigInteger[encriptado.length];

        for(int i = 0; i<desencriptar.length; i++){
            desencriptar[i] = encriptado[i].modPow(d, n);
        }

        char[] charArray = new char[desencriptar.length];

        for(int i = 0; i<charArray.length; i++){
            charArray[i] = (char)(desencriptar[i].intValue());
        }

        return (new String(charArray));
    }
}