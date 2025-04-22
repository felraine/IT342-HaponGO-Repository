import React, { useState, useRef, useEffect } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';  // Import useNavigate

export default function PaymentScreen() {
  const [amount, setAmount] = useState('100');
  const [description, setDescription] = useState('HaponGO Premium');
  const [remarks, setRemarks] = useState('One Time Payment');
  const [isLoading, setIsLoading] = useState(false);
  const [paymentStatus, setPaymentStatus] = useState('Pending...');
  const [paymentId, setPaymentId] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [modalMessage, setModalMessage] = useState('');
  const pollingInterval = useRef(null);

  const key = import.meta.env.VITE_PAYMONGO_KEY;
  const basicAuth = btoa(`${key}:`);
  const navigate = useNavigate();  // Initialize useNavigate

  const handlePayment = async () => {
    if (!amount || isNaN(amount)) {
      alert('Please enter a valid amount');
      return;
    }
    setIsLoading(true);
    setPaymentStatus('Pending...');
    setPaymentId(null);

    const options = {
      method: 'POST',
      url: 'https://api.paymongo.com/v1/links',
      headers: {
        accept: 'application/json',
        'content-type': 'application/json',
        authorization: `Basic ${basicAuth}`,
      },
      data: {
        data: {
          attributes: {
            amount: parseFloat(amount) * 100,
            description,
            remarks,
          },
        },
      },
    };

    try {
      const response = await axios.request(options);
      const { id, attributes } = response.data.data;
      setPaymentId(id);
      if (attributes.checkout_url) {
        window.open(attributes.checkout_url, '_blank');
      }
      startPollingStatus(id);
    } catch (error) {
      console.error('Error creating payment link:', error);
      alert('Payment link creation failed.');
    } finally {
      setIsLoading(false);
    }
  };

  const startPollingStatus = (id) => {
    if (pollingInterval.current) clearInterval(pollingInterval.current);
    pollingInterval.current = setInterval(async () => {
      try {
        const response = await axios.get(`https://api.paymongo.com/v1/links/${id}`, {
          headers: { accept: 'application/json', authorization: `Basic ${basicAuth}` },
        });
        const status = response.data.data.attributes.status;
        setPaymentStatus(status);

        if (['paid', 'cancelled', 'expired'].includes(status)) {
          clearInterval(pollingInterval.current);
          pollingInterval.current = null;
          setModalMessage(
            status === 'paid' ? '✅ Payment Successful!' : '❌ Payment Cancelled or Expired.'
          );
          setShowModal(true);

          if (status === 'paid') {
            confirmUserSubscription();
          }
        }
      } catch (error) {
        console.error('Error fetching payment status:', error);
      }
    }, 5000);
  };

  const confirmUserSubscription = async () => {
    try {
      //production https://hapongo-backend-819908927275.asia-southeast1.run.app/api/lesson-contents/lesson/${lessonId}
      //development http://localhost:8080/api/lesson-contents/lesson/${lessonId}
      // Replace with your user's ID
      const userId = localStorage.getItem('userId');
      if (!userId) {
        console.error("User ID not found in localStorage.");
        return;
      }
      await axios.put(`http://localhost:8080/api/users/confirm_payment/${userId}`);
      console.log('✅ User subscription confirmed on backend.');
    } catch (error) {
      console.error('Error confirming payment on server:', error);
    }
  };

  useEffect(() => {
    return () => {
      if (pollingInterval.current) clearInterval(pollingInterval.current);
    };
  }, []);

  // Redirect to /dashboard after closing the modal
  const handleCloseModal = () => {
    setShowModal(false);
    navigate('/dashboard');  // Redirect to the dashboard
  };

  return (
    <div className="min-h-screen bg-[#FFE79B] flex flex-col relative">
      {showModal && (
        <div className="fixed inset-0 bg-opacity-50 flex justify-center items-center z-50" style={{ background: "rgba(0, 0, 0, 0.3)" }}>
          <div className="bg-white p-6 rounded-2xl shadow-lg text-center max-w-sm">
            <p className="text-xl mb-4">{modalMessage}</p>
            <button
              onClick={handleCloseModal}  // Use the new close handler
              className="px-4 py-2 bg-green-600 text-white rounded-full hover:bg-green-700"
            >
              Close
            </button>
          </div>
        </div>
      )}

      <header className="w-full font-sans">
        <h1 className="m-0 text-[40px] text-white bg-[#BC002D] font-bold py-3 pl-20 text-left">
          HaponGO
        </h1>
      </header>

      <main className="flex flex-col items-center justify-center flex-grow p-6">
        <h2 className="text-3xl font-bold mb-3 text-center font-sans">
          Go beyond the basics with <span className="text-[#E63946]">Premium</span> Access!
        </h2>

        <div className="flex flex-col md:flex-row gap-8 w-full max-w-5xl mt-6">
          <div className="flex-1 bg-white rounded-2xl p-6 shadow-lg border border-gray-300 flex flex-col justify-between">
            <div>
              <h3 className="text-xl font-bold text-green-600 mb-4">Your Subscription</h3>
              <div className="p-4 border-b text-lg">
                HaponGO premium — <strong>100.00 PHP</strong>
              </div>
              <h4 className="text-lg font-semibold text-green-600 mt-6">What Happens Next?</h4>
              <p className="mt-2 text-gray-700 text-sm">
                You’ll be redirected to PayMongo’s secure checkout to complete your payment.
              </p>
            </div>

            <div className="mt-6">
              <button
                onClick={handlePayment}
                disabled={isLoading}
                className={`w-full py-3 text-white rounded-xl text-lg font-semibold transition ${
                  isLoading ? 'bg-green-300 cursor-not-allowed' : 'bg-green-600 hover:bg-green-700'
                }`}
              >
                {isLoading ? 'Processing...' : 'Proceed to Payment'}
              </button>

              <p className="mt-4 text-sm text-gray-700 text-center">
                Current Status: <strong>{paymentStatus.toUpperCase()}</strong>
              </p>
            </div>
          </div>

          <div className="flex-1 bg-white rounded-2xl shadow-lg border border-gray-300 text-center flex flex-col justify-between overflow-hidden">
            <div className="bg-teal-500 p-8 flex flex-col items-center justify-center">
              <div className="w-24 h-24 mb-2 flex items-center justify-center">
                <img src="icon-shib.png" alt="profile" className="w-full h-full object-cover" />
              </div>
              <h3 className="text-lg font-bold text-white">Included with HaponGO Plus:</h3>
            </div>

            <div className="p-6 flex flex-col justify-between flex-grow">
              <ul className="text-left space-y-3 text-green-700 mb-6">
                <li>✔ Unlimited quizzes & real-life conversations</li>
                <li>✔ Full access to all lessons</li>
                <li>✔ No ads for a seamless experience</li>
                <li>✔ Supports offline mode</li>
              </ul>

              <div>
                <p className="text-2xl font-bold">100.00 PHP</p>
                <p className="text-sm text-gray-500">ONE TIME PAYMENT</p>
              </div>
            </div>
          </div>
        </div>

        <p className="mt-12 text-xs text-gray-500">Powered by PayMongo</p>
      </main>
    </div>
  );
}
