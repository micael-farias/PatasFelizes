package main.utils;
public class EmailTemplate {

    public static String criarCorpoEmail(String descricaoTarefa, String dataTarefa, String nomeVoluntario) {
        return """
                        <!DOCTYPE html>
                             <html lang="en">
                             <head>
                                 <meta charset="UTF-8">
                                 <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                 <style>
                                     body {
                                         margin: 0;
                                         padding: 0;
                                     }
                             
                                     .container {
                                         width: 100%;
                                         max-width: 676px;
                                         margin: 0 auto;
                                         position: relative;
                                         background: white;
                                     }
                             
                                     .background-top {
                                         width: 100%;
                                         height: 280px;
                                         background: #C2D5FA;
                                     }
                             
                                     .image {
                                         width: 100%;
                                         max-width: 335px;
                                         height: auto;
                                         position: absolute;
                                         left: 50%;
                                         top: 38px;
                                         transform: translateX(-50%);
                                     }
                             
                                     .background-bottom {
                                         width: 100%;
                                         height: 271px;
                                         background: #84A1DB;
                                     }
                             
                                     .content {
                                         position: absolute;
                                         left: 65px;
                                         top: 336px;
                                         color: white;
                                         font-family: Helvetica, sans-serif;
                                         word-wrap: break-word;
                                     }
                             
                                     .description {
                                         font-size: 20px;
                                         font-weight: 400;
                                         margin-top: 10px;
                                     }
                                 </style>
                             </head>
                             <body>
                                 <div class="container">
                                     <div class="background-top"></div>
                                     <img class="image" src="https://firebasestorage.googleapis.com/v0/b/cloud-storage-33fb2.appspot.com/o/Ativo%208.png?alt=media&token=2b694b6e-31ae-409a-a8d9-85f13d67d46f" />
                                     <div class="background-bottom"></div>
                                     <div class="content">
                                         <div style="font-size: 25px; font-weight: 600; word-wrap: break-word">Olá, Michael!</div>
                                         <div style="color: #536B9D; font-size: 30px; font-weight: 600; word-wrap: break-word">Nova tarefa cadastrada</div>
                                         <div class="description">Você foi mencionado na criação da tarefa descrita por<br/>"Voce tem a tarefa de ficar quieto e na sua".</div>
                                         <div class="description">A tarefa foi cadastrada para o dia<br/>Sexta-feira, 08/12/2023</div>
                                     </div>
                                 </div>
                             </body>
                             </html>
                             """
                .replace("[dataTarefa]", dataTarefa)
                .replace("[descricaoTarefa]", descricaoTarefa)
                .replace("[voluntario]", nomeVoluntario);
                
    }

}
