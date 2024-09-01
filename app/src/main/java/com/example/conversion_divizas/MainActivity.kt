package com.example.conversion_divizas

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.conversion_divizas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val conversionRates = mapOf(
        "MXM" to mapOf(
            "USD" to 0.05,
            "EUR" to 0.045,
            "BS" to 0.10,
            "JPY" to 5.68
        ),
        "USD" to mapOf(
            "MXM" to 20.0,
            "EUR" to 0.85,
            "BS" to 2.0,
            "JPY" to 110.0
        ),
        "EUR" to mapOf(
            "MXM" to 22.0,
            "USD" to 1.18,
            "BS" to 2.35,
            "JPY" to 130.0
        ),
        "BS" to mapOf(
            "MXM" to 10.0,
            "USD" to 0.50,
            "EUR" to 0.43,
            "JPY" to 55.0
        ),
        "JPY" to mapOf(
            "MXM" to 0.18,
            "USD" to 0.009,
            "EUR" to 0.0077,
            "BS" to 0.018
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Adaptadores para los spinners
        val adapterInicial = ArrayAdapter.createFromResource(
            this,
            R.array.currencies_array,
            android.R.layout.simple_spinner_item
        )
        adapterInicial.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.divisasIniciales.adapter = adapterInicial

        val adapterFinal = ArrayAdapter.createFromResource(
            this,
            R.array.currencies_array_final,
            android.R.layout.simple_spinner_item
        )
        adapterFinal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.divisasFinales.adapter = adapterFinal


        binding.conversionDivisas.setOnClickListener {
            val divisaInicial = binding.divisasIniciales.selectedItem.toString().split(" - ")[0]
            val divisaFinal = binding.divisasFinales.selectedItem.toString().split(" - ")[0]
            val valorString = binding.valorDivisa.text.toString()

            if (valorString.isNotEmpty()) {
                val valor = valorString.toDouble()
                val resultado = convertirDivisas(divisaInicial, divisaFinal, valor)

                if (resultado != null) {
                    binding.divisasHolder.text = "Resultado: $resultado $divisaFinal"
                } else {
                    binding.divisasHolder.text = "Error: No se encontró una tasa de conversión entre $divisaInicial y $divisaFinal."
                }
            } else {
                binding.divisasHolder.text = "Por favor, ingresa un valor para convertir."
            }
        }

    }


    private fun convertirDivisas(divisaInicial: String, divisaFinal: String, valor: Double): Double? {
        val tasaConversion = conversionRates[divisaInicial]?.get(divisaFinal)

        return tasaConversion?.let { valor * it }
    }
}