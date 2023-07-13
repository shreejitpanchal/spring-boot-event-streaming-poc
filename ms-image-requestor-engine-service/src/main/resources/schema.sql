--DROP TABLE IF EXISTS customer_master;

CREATE TABLE IF NOT EXISTS image_service (
  id INT AUTO_INCREMENT PRIMARY KEY,
  image_id VARCHAR(250) NOT NULL,
  user_id VARCHAR(250) NOT NULL,
  customer_name VARCHAR(250) NOT NULL,
  image_filename VARCHAR(250) NOT NULL,
  image_type VARCHAR(250) NOT NULL,
  image_stream_status VARCHAR(250) DEFAULT NULL,
  image_stream_desc VARCHAR(250) DEFAULT NULL,
  correlation_id VARCHAR(250) DEFAULT NULL,
  transaction_id VARCHAR(250) DEFAULT NULL,
  request_datetime VARCHAR(250) DEFAULT NULL,
  created_datetime VARCHAR(250) DEFAULT NULL,
  modified_datetime VARCHAR(250) DEFAULT NULL,
  created_by VARCHAR(250) DEFAULT NULL,
  modified_by VARCHAR(250) DEFAULT NULL
);