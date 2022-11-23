/*1. Realiza una aplicación que genere un fichero de acceso directo con registros de una determinada entidad.
La aplicación debe permitir, al menos, generar altas y consultas.
 */
package com.fernandogil.ut1;
import java.io.*;
import java.util.Scanner;

public class rafTenista {
    public static final int TAMANO = 88; // 2 Strings(20 caracteres(40bytes cada uno) * 2) + 2 int(4bytes cada uno)= 88 bytes
    public static int numRegistros = 10; //CANTIDAD DE REGISTROS QUE TENDRÁ EL ARCHIVO
    private static RandomAccessFile raf= null;

    private static int idRanking=0;
    private static String nom="";
    private static String apellido="";
    private static int grandSlams=0;
    private static StringBuffer sbNom=null;
    private static StringBuffer sbApe=null;
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws FileNotFoundException,IOException{

        raf = new RandomAccessFile("rafTenistas.dat","rw");
        raf.setLength(10);  //RANKING DE LOS 10 MEJORES TENISTAS
        raf.seek((raf.length()-1)*TAMANO);
        raf.writeInt(10 );     //ESCRIBE UN INT EN LA ÚLTIMA POSICIÓN PARA QUE EL FICHERO NO ESTÉ VACÍO UNA VEZ LLAMEMOS AL MÉTODO READINT()

        while (true){
            System.out.println("Escoja una opción:\n1.-Alta tenista\n2.-Consulta tenista\n3.-Modifica tenista\n4.-Listar registros\n0.-Salir");
            int opc = sc.nextInt();
            switch (opc){
                case 1: altaTenista(raf); break;
                case 2: consultaTenista(raf); break;
                case 3: modificaTenista(raf); break;
                case 4: listaRegistros(raf); break;
                default:
                    raf.close();
                    System.exit(0);
            }
        }
    }
    public static void altaTenista(RandomAccessFile raf)throws EOFException,FileNotFoundException,IOException{
        File archivoDuplicados = new File("archivoDuplicados.dat");
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(archivoDuplicados, true));

        System.out.println("Introduciendo datos del tenista...");
        sc.nextLine();

        System.out.println("Introduzca la posición que ocupará en el ranking");
        int idRanking = sc.nextInt();
        sc.nextLine();
        System.out.println("Nombre del tenista:");
        nom = sc.nextLine();
        sbNom = new StringBuffer(nom);
        System.out.println("Apellido del tenista:");
        apellido = sc.nextLine();
        sbApe = new StringBuffer(apellido);
        //ESTABLECEMOS LA LONGITUD DE LOS BUFFER DE MANERA QUE SOLO ACEPTE 20 CARACTERES
        sbNom.setLength(20);
        sbApe.setLength(20);
        System.out.println("Grand-Slams conseguidos:");
        grandSlams = sc.nextInt();
        sc.nextLine();
        raf.seek((idRanking - 1) * TAMANO);

        if (idRanking != raf.readInt() || idRanking == numRegistros) {     //COMPRUEBA QUE EL REGISTRO ESTÁ LIBRE O SI ES EL ÚLTIMO
            raf.seek(TAMANO * (idRanking - 1));
            raf.writeInt(idRanking);
            raf.writeUTF(sbNom.toString());
            raf.writeUTF(sbApe.toString());
            raf.writeInt(grandSlams);
            System.out.println("----------- ¡Ha añadido un nuevo tenista al registro! ---------------");
        } else {        //SI ESTÁ DUPLICADO
            dos.writeInt(idRanking);
            dos.writeUTF(sbNom.toString());
            dos.writeUTF(sbApe.toString());
            dos.writeInt(grandSlams);
            System.out.println("----------- ¡Registro duplicado, se añadirá automáticamente al fichero 'ArchivosDuplicados.dat' ---------------");
        }

    }


    public static void consultaTenista(RandomAccessFile raf) throws EOFException,IOException {

        System.out.println("Introduzca número del registro que desea consultar:");
        idRanking = sc.nextInt();

        raf.seek((idRanking-1)*TAMANO);
        idRanking = raf.readInt();
        nom = raf.readUTF();
        apellido = raf.readUTF();
        grandSlams = raf.readInt();
        System.out.println("Puesto del ranking: " + idRanking+ "\t\tNombre: "+nom.trim()+"\t\tApellido: "+apellido.trim()+"\t\tGrand-Slams conseguidos: "+grandSlams+"\n");

    }

    public static void modificaTenista(RandomAccessFile raf) throws IOException{

        System.out.println("Escoja la posición del ranking que desea modificar: ");
        idRanking = sc.nextInt();
        sc.nextLine();
        raf.seek((idRanking-1)*TAMANO);

        System.out.println("Nombre del nuevo tenista: ");
        nom = sc.nextLine();
        sbNom = new StringBuffer(nom);
        sbNom.setLength(20);
        System.out.println("Apellido del nuevo tenista: ");
        apellido = sc.nextLine();
        sbApe = new StringBuffer(apellido);
        sbApe.setLength(20);
        System.out.println("Grand-Slams conseguidos por el nuevo tenista: ");
        grandSlams = sc.nextInt();

        raf.writeInt(idRanking);
        raf.writeUTF(sbNom.toString());
        raf.writeUTF(sbApe.toString());
        raf.writeInt(grandSlams);

        System.out.println("\n¡Tenista modificado con éxito!\n");

    }

    public static void listaRegistros(RandomAccessFile raf){
        int cont = 1;
        try {

            while (cont <= numRegistros){       //BUCLE QUE LEE TODOS LOS REGISTROS
                if(cont == 1)
                    raf.seek(TAMANO * (cont - 1));
                else
                    raf.seek((cont - 1) * TAMANO);
                idRanking = raf.readInt();
                if(idRanking == 0){
                    cont++;
                    continue;
                }

                nom = raf.readUTF().trim();
                apellido = raf.readUTF().trim();
                grandSlams = raf.readInt();

                System.out.println("Ranking: " + idRanking);
                System.out.println("Nombre: " + nom);
                System.out.println("Apellido: " + apellido);
                System.out.println("Grand-Slams ganados: " + grandSlams+"\n-----------------------------");
                cont++;
            }

        } catch (EOFException eo) {
            eo.printStackTrace();
        }catch (IOException io){
            io.printStackTrace();
        }

    }


}