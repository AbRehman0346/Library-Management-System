//This is the code for encrypt password jar file which is added to the project libraries...
//this code here is for safe keeping and not used anywhere on the project... Thanks.
package jarfile_code;

class EncryptPassword{
        public String Encrypt(String password){
            password = UpperCase_Alphabetical_Character_Encryption_Decryption_Function(
                    LowerCase_Alphabetical_Characters_Encryption_Function(
                            Number_Decrypt_by_ASCII_Code(Encrypt_Decrypt_Special_Signs(password))));
            return password;
        }

        public String UpperCase_Alphabetical_Character_Encryption_Decryption_Function(String a){
//    Encrypting UpperCase Alphabetical Characters...
            StringBuffer sb = new StringBuffer();
            for (int i=0; i<a.length(); i++){
                if (a.charAt(i) >= 65 && a.charAt(i) <= 90){
                    int passwordNumber = ((int)a.charAt(i) - 64)-1;
                    int encryptedPassword = (26 - passwordNumber) + 64;
                    sb.append((char)encryptedPassword);
                }
                else
                    sb.append(a.charAt(i));
            }
            return sb.toString();
        }

        public String LowerCase_Alphabetical_Characters_Encryption_Function(String value){
//    Encrypting LowerCase Alphabetical Characters...
            StringBuffer sb = new StringBuffer();
            for (int i=0; i<value.length(); i++){
                if ((value.charAt(i) >= 97 && value.charAt(i) <= 122)) {
                    int newValue = (26 - ((value.charAt(i) - 96) - 1)) + 96;
                    sb.append((char) newValue);
                }
                else
                    sb.append(value.charAt(i));
            }
            return sb.toString();
        }

        public String Encrypt_Decrypt_Special_Signs(String password){
//    Encrypting special Signs....
            StringBuffer sb = new StringBuffer();
            for (int i=0; i<password.length(); i++){
                if(password.charAt(i) == 32){sb.append("^");}
                else if(password.charAt(i) == 33){sb.append("@");}
                else if(password.charAt(i) == 35){sb.append("?");}
                else if(password.charAt(i) == 36){sb.append("/");}
                else if(password.charAt(i) == 37){sb.append("-");}
                else if(password.charAt(i) == 38){sb.append("+");}
                else if(password.charAt(i) == 42){sb.append("*");}
                else if(password.charAt(i) == 43){sb.append("&");}
                else if(password.charAt(i) == 45){sb.append("%");}
                else if(password.charAt(i) == 47){sb.append("$");}
                else if(password.charAt(i) == 63){sb.append("#");}
                else if (password.charAt(i) == 64){sb.append("!");}
                else if(password.charAt(i) == 94){sb.append(" ");}
                else{sb.append(password.charAt(i));}
            }
            return sb.toString();
        }

        public String Number_Decrypt_by_ASCII_Code(String value){
            StringBuffer sb = new StringBuffer();
            for (int i=0; i<value.length(); i++){
                if (value.charAt(i) >= 48 && value.charAt(i) <= 57){
                    int number = ((9-((value.charAt(i) - 48)-1))-1)+48;
                    sb.append((char)number);
                }
                else
                    sb.append(value.charAt(i));
            }
            return sb.toString();
        }
    }
