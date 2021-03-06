/*
 * Copyright (c) 2019 Adyen N.V.
 *
 * This file is open source and available under the MIT license. See the LICENSE file for more info.
 *
 * Created by arman on 10/10/2019.
 */

package com.adyen.checkout.example.repositories.paymentMethods

import com.adyen.checkout.base.model.PaymentMethodsApiResponse
import com.adyen.checkout.example.data.api.CheckoutApiService
import com.adyen.checkout.example.data.api.model.paymentsRequest.PaymentMethodsRequest
import com.adyen.checkout.example.repositories.BaseRepository
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call

interface PaymentsRepository {
    suspend fun getPaymentMethods(paymentMethodsRequest: PaymentMethodsRequest): PaymentMethodsApiResponse?
    fun paymentsRequest(paymentsRequest: RequestBody): Call<ResponseBody>
    fun detailsRequest(paymentsRequest: RequestBody): Call<ResponseBody>
}

class PaymentsRepositoryImpl(private val checkoutApiService: CheckoutApiService) : PaymentsRepository, BaseRepository() {

    override suspend fun getPaymentMethods(paymentMethodsRequest: PaymentMethodsRequest): PaymentMethodsApiResponse? {
        return safeApiCall(
            call = { checkoutApiService.paymentMethodsAsync(paymentMethodsRequest).await() }
        )
    }

    override fun paymentsRequest(paymentsRequest: RequestBody): Call<ResponseBody> {
        return checkoutApiService.payments(paymentsRequest)
    }

    override fun detailsRequest(paymentsRequest: RequestBody): Call<ResponseBody> {
        return checkoutApiService.details(paymentsRequest)
    }
}
