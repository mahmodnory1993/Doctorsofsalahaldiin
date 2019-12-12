package org.codeforiraq.doctor

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_data__user__docotr.*

class Data_of_User_and_Docotr : AppCompatActivity() {

    lateinit var uriimage : Uri
    lateinit var uriimage2 : Uri




    lateinit var mohafada :Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data__user__docotr)

        mohafada =findViewById(R.id.mohafada)



        var mohafadaarray = arrayOf("صلاح الدين")


        var adapter_mohafada  = ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mohafadaarray)


        mohafada.adapter = adapter_mohafada


        image_doctor_btn.setOnClickListener {

            getimage()
        }


        savedata.setOnClickListener {

            addDoctor()
        }
    }

    fun addDoctor(){


        var name1 =name.text.toString()
        var department_doctor1 =department_doctor.text.toString()
        var addres_doctor1 =addres_doctor.text.toString()
        var phonenum_doctor1 =phonenum_doctor.text.toString()
        var signup_doctor1 =signup_doctor.text.toString()
        var city1 =city.text.toString()


        var doctorDatabase=FirebaseDatabase.getInstance().getReference("Users")
        var doctorid =doctorDatabase.push().key
        var myAuth = FirebaseAuth.getInstance().currentUser!!.uid



        if (name1.isEmpty()
            and department_doctor1.isEmpty()
            and addres_doctor1.isEmpty()
            and phonenum_doctor1.isEmpty()
            and signup_doctor1.isEmpty()
            and city1.isEmpty()) {

            name.error = "ادخل الاسم"
            department_doctor.error = "ادخل الاختصاص"
            addres_doctor.error = "ادخل العنوان"
            phonenum_doctor.error = "ادخل رقم الهاتف"
            signup_doctor.error = "ادخل رقم تسجيل الاجازة"
            city.error = "ادخل رقم تسجيل الاجازة"

            return
        }


        var storage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://doctorsofsalahaldin.appspot.com/")
        var imagepath=doctorid + ".jpg"
        var upload = storage.child("personal_image/" + imagepath)
        var upload2 = storage.child("doctor_sinied_image/" + imagepath)



        Toast.makeText(this, "جاري رفع المعلومات", Toast.LENGTH_LONG).show()

        upload.putFile(uriimage).addOnSuccessListener {

            upload.downloadUrl.addOnSuccessListener { it1 ->




                        var doctordatainsert = DoctorData(
                            doctorid!!, name1, department_doctor1, addres_doctor1, phonenum_doctor1, signup_doctor1, city1,
                            "صلاح الدين".toString(), it1.toString(), "طبيب"
                        )



                doctorDatabase.child("Doctor").child(doctorid!!).setValue(doctordatainsert)
                            .addOnCompleteListener {

                                Toast.makeText(this, "تم الحفظ", Toast.LENGTH_LONG).show()

                                var intent = Intent(this, Show_for_doctor::class.java)
                                startActivity(intent)
                                this.finish()

                            }



            }
            Toast.makeText(this@Data_of_User_and_Docotr,"تم اضافة عنصر !",Toast.LENGTH_LONG).show()


        }.addOnFailureListener {
            Toast.makeText(this,"لم يتم رفع الصورة تأكد من اتصالك بالانترنت...",Toast.LENGTH_LONG).show()


        }






    }
//    fun addUser (){
//
//
//        var name1 =name.text.toString()
//        var city1 =city.text.toString()
//
//
//        var userDatabase=FirebaseDatabase.getInstance().getReference("Users")
//        var userid =userDatabase.push().key
//        var myAuth = FirebaseAuth.getInstance().currentUser!!.uid
//
//
//        if (name1.isEmpty()&&city1.isEmpty()) {
//
//            name.error = "ادخل الاسم"
//            city.error = "ادخل المدينة"
//
//            return
//        }
//
//
//        var storage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://doctorsofsalahaldin.appspot.com/")
//        var imagepath=myAuth + ".jpg"
//        var upload = storage.child("personal_image/" + imagepath)
//
//
//
//        upload.putFile(uriimage).addOnSuccessListener {
//
//            upload.downloadUrl.addOnSuccessListener {
//
//                var userdatainsert = UserData(myAuth,name1,it.toString(),city1,"صلاح الدين","مستخدم عادي")
//
//                userDatabase.child("User").child(myAuth!!).setValue(userdatainsert).addOnCompleteListener {
//
//                    Toast.makeText(this, "تم الحفظ", Toast.LENGTH_LONG).show()
//
//                    var intent = Intent(this,Show_for_user::class.java)
//                    startActivity(intent)
//
//                }
//
//            }
//                Toast.makeText(this@Data_of_User_and_Docotr,"تم اضافة عنصر !",Toast.LENGTH_LONG).show()
//
//
//        }.addOnFailureListener {
//            Toast.makeText(this,"لم يتم رفع الصورة تأكد من اتصالك بالانترنت...",Toast.LENGTH_LONG).show()
//
//
//        }
//
//
//    }

    //الصورة الشخصية
    fun getimage() {

        var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent,0)
    }

//    fun getimage2() {
//
//        var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        startActivityForResult(intent,1)
//    }



    override  fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==0 && data!=null && resultCode == RESULT_OK){

            uriimage = data!!.data!!
            var colum = arrayOf(MediaStore.Images.Media.DATA)
            var query = contentResolver.query(uriimage!!,colum,null,null,null,null)

            query!!.moveToFirst()
            var columIndex = query.getColumnIndex(colum[0])
            var path =query.getString(columIndex)
            image_doctor.setImageBitmap(BitmapFactory.decodeFile(path))


        }

    }


}



