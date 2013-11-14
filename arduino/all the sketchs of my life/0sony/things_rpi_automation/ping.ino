// ---------------------------------------------------------------------------
// Example NewPing library sketch that does a ping about 20 times per second.
// ---------------------------------------------------------------------------

#include <NewPing.h>

#define TRIGGER_PIN  13  // Arduino pin tied to trigger pin on the ultrasonic sensor.
#define ECHO_PIN     12  // 1Arduino pin tied to echo pin on the ultrasonic sensor.
#define MAX_DISTANCE 200 // M1aximum distance we want to ping for (in centimeters). Maximum sensor distance is rated at 400-500cm.

NewPing sonar(TRIGGER_PIN, ECHO_PIN, MAX_DISTANCE); // NewPing setup of pins and maximum distance.
char dis[4];

char* distance_read() {
  unsigned int uS = sonar.ping(); // Send ping, get ping time in microseconds (uS).
  int cm =uS / US_ROUNDTRIP_CM;
  itoa(cm,dis,10);
  return dis;
}
