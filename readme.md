Card number masking application:
- reads log files from ~/log/unmasked
- masks card number (e.g.) 4444333322221111 -> ************1111
- prints log file with the same name to ~/log/masked


Testing:
- run main in CardNumberMasker class
- copy log file to ~/log/unmasked (example is in ~/log)
- verify output in ~/log/masked

Possible improvements (skipped to for time-efficiency):
- Add integration tests
- enhance build (e.g. linter, coverage checks) 