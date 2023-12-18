import os

os.environ["TF_CPP_MIN_LOG_LEVEL"] = "2"

import io
from tensorflow import keras
import numpy as np
from PIL import Image
from flask import Flask, request, jsonify

model = keras.models.load_model("toymodel.h5")

label = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
nama_mainan = [
    "Bebek karet",
    "Fidget spinner",
    "Ketapel",
    "Mainan pedang",
    "Kelereng",
    "Lato-lato",
    "Layang-layang",
    "Pistol mainan",
    "Puzzle",
    "Robot",
]

deskripsi_mainan = [
    "Berbahaya bagi anak,  paparan phthalates dapat mengganggu keseimbangan hormon perkembangan pada anak.",
    "Dapat dimainkan tetapi harus dalam pengawasan.",
    "Berbahaya bagi anak, dapat melukai fisik.",
    "Berbahaya bagi anak, dapat melukai fisik.",
    "Dapat dimainkan tetapi harus dalam pengawasan.",
    "Dapat dimainkan tetapi harus dalam pengawasan.",
    "Mainan aman untuk dimainkan oleh anak anak.",
    "Berbahaya bagi anak, dapat melukai fisik.",
    "Mainan aman untuk dimainkan oleh anak anak.",
    "Mainan aman untuk dimainkan oleh anak anak.
",
]

app = Flask(_name_)


def predict_label(img):
    i = np.asarray(img) / 255.0
    i = i.reshape(1, 224, 224, 3)
    pred = model.predict(i)
    result = str(np.argmax(pred))
    return result


@app.route("/predict", methods=["GET", "POST"])
def index():
    file = request.files.get("file")
    if file is None or file.filename == "":
        return jsonify(
            {
                "error": True,
                "message": "File tidak ditemukan",
                "result": {
                    "toy_name": "",
                    "description": "",
                },
            }
        )

    image_bytes = file.read()
    img = Image.open(io.BytesIO(image_bytes))
    img = img.resize((224, 224), Image.NEAREST)
    pred_label = predict_label(img)
    indeks_toy = int(pred_label)

    # Create a JSON response
    response_data = {
        "error": False,
        "message": "Success",
        "result": {
            "toy_name": nama_mainan[indeks_toy],
            "description": deskripsi_mainan[indeks_toy],
        },
    }
    return jsonify(response_data)


if _name_ == "_main_":
    app.run(debug=True)