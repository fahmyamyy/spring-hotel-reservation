INSERT INTO public.payment_method (payment_method_id, name) VALUES (1, 'Cash');
INSERT INTO public.payment_method (payment_method_id, name) VALUES (2, 'Debit');
INSERT INTO public.payment_method (payment_method_id, name) VALUES (3, 'QR');

INSERT INTO public.reservation_status (reservation_status_id, name) VALUES (1, 'Waiting for payment');
INSERT INTO public.reservation_status (reservation_status_id, name) VALUES (2, 'Paid');
INSERT INTO public.reservation_status (reservation_status_id, name) VALUES (3, 'Canceled');
-- INSERT INTO public.reservation_status (reservation_status_id, name) VALUES (4, 'Canceled');
-- ('QR');
--
-- INSERT INTO public.reservation_status
-- ("name")
-- VALUES
-- ('Waiting for payment'),
-- ('Paid'),
-- ('Done'),
-- ('Canceled');